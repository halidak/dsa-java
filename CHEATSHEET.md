# Pattern cheatsheet

What to look for in a problem statement, and a Java skeleton for each pattern. When a problem teaches me a new trick, I add one line about it under its pattern.

---

## Arrays & Hashing
**Look for:** "contains / count / group / have we seen this before", anagrams, frequencies.

```java
Map<Integer, Integer> freq = new HashMap<>();
for (int x : nums) freq.merge(x, 1, Integer::sum);
```
- Group anagrams: use the sorted string (or a 26-letter count array) as the key.
- Top K frequent elements: bucket sort by frequency gives O(n).
- Prefix sums: `prefix[i + 1] = prefix[i] + nums[i]`, and the sum of `[l, r]` is `prefix[r + 1] - prefix[l]`.

**Tricks I learned:**
-

---

## Two Pointers
**Look for:** a sorted array, pairs or triplets with a target sum, palindromes, in-place rearranging.

```java
int l = 0, r = nums.length - 1;
while (l < r) {
    int sum = nums[l] + nums[r];
    if (sum == target) { /* record the answer */ l++; r--; }
    else if (sum < target) l++;
    else r--;
}
```
- 3Sum: sort first, fix `i`, run two pointers on the rest, and skip duplicates.
- Container With Most Water: always move the shorter wall.

**Tricks I learned:**
-

---

## Sliding Window
**Look for:** "longest / shortest contiguous subarray or substring such that…".

```java
int l = 0, best = 0;
Map<Character, Integer> window = new HashMap<>();
for (int r = 0; r < s.length(); r++) {
    window.merge(s.charAt(r), 1, Integer::sum);       // grow
    while (/* window is invalid */ false) {           // shrink
        window.merge(s.charAt(l), -1, Integer::sum);
        l++;
    }
    best = Math.max(best, r - l + 1);
}
```
- Fixed-size window: add `nums[r]`, and once `r >= k`, remove `nums[r - k]`.
- Sliding Window Maximum: a monotonic deque that stores indices.

**Tricks I learned:**
-

---

## Stack
**Look for:** matching brackets, "next greater / smaller element", evaluating expressions.

```java
Deque<Integer> stack = new ArrayDeque<>();           // monotonic stack of indices
int[] answer = new int[n];
for (int i = 0; i < n; i++) {
    while (!stack.isEmpty() && temps[i] > temps[stack.peek()]) {
        int j = stack.pop();
        answer[j] = i - j;
    }
    stack.push(i);
}
```
- Use `ArrayDeque`, not `Stack` (Stack is synchronized and legacy).

**Tricks I learned:**
-

---

## Binary Search
**Look for:** a sorted input, "minimum X such that…", answers that are monotonic (search over the answer space).

```java
int lo = 0, hi = n - 1;
while (lo <= hi) {
    int mid = lo + (hi - lo) / 2;                    // avoids overflow
    if (nums[mid] == target) return mid;
    if (nums[mid] < target) lo = mid + 1; else hi = mid - 1;
}
return -1;
```
```java
// first value where ok(x) is true (Koko Eating Bananas, Capacity To Ship Packages)
int lo = min, hi = max;
while (lo < hi) {
    int mid = lo + (hi - lo) / 2;
    if (ok(mid)) hi = mid; else lo = mid + 1;
}
return lo;
```

**Tricks I learned:**
-

---

## Linked List
**Look for:** reversing, merging, cycles, "k-th from the end".

```java
ListNode prev = null, cur = head;                    // reverse
while (cur != null) { ListNode next = cur.next; cur.next = prev; prev = cur; cur = next; }

ListNode slow = head, fast = head;                   // middle / cycle (Floyd)
while (fast != null && fast.next != null) { slow = slow.next; fast = fast.next.next; }

ListNode dummy = new ListNode(0, head);              // a dummy node removes head edge cases
```
- LRU Cache: a HashMap plus a doubly linked list (or `LinkedHashMap` with `accessOrder = true`).

**Tricks I learned:**
-

---

## Trees
**Look for:** almost any tree problem. Decide between DFS (recursion) and BFS (by levels).

```java
int dfs(TreeNode node) {                             // post-order: children first, then combine
    if (node == null) return 0;
    int left = dfs(node.left), right = dfs(node.right);
    return 1 + Math.max(left, right);
}
```
```java
Queue<TreeNode> q = new ArrayDeque<>(List.of(root)); // BFS by levels
while (!q.isEmpty()) {
    int size = q.size();
    for (int i = 0; i < size; i++) {
        TreeNode node = q.poll();
        if (node.left != null) q.offer(node.left);
        if (node.right != null) q.offer(node.right);
    }
}
```
- Validate BST: pass `(low, high)` bounds down the recursion; don't just compare with the children.
- An in-order traversal of a BST visits the values in sorted order.

**Tricks I learned:**
-

---

## Tries
**Look for:** prefixes, autocomplete, searching for many words at once.

```java
class TrieNode { TrieNode[] next = new TrieNode[26]; boolean end; }
```

**Tricks I learned:**
-

---

## Heap / Priority Queue
**Look for:** "k largest / smallest / closest", merging k sorted lists, a running median.

```java
PriorityQueue<Integer> minHeap = new PriorityQueue<>();               // keep the k largest
for (int x : nums) { minHeap.offer(x); if (minHeap.size() > k) minHeap.poll(); }

PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);  // order arrays by a field
```
- Running median: a max-heap for the lower half and a min-heap for the upper half.

**Tricks I learned:**
-

---

## Backtracking
**Look for:** "all combinations / permutations / subsets", placing items on a board.

```java
void backtrack(int start, List<Integer> path) {
    result.add(new ArrayList<>(path));               // copy, don't store the live list
    for (int i = start; i < nums.length; i++) {
        if (i > start && nums[i] == nums[i - 1]) continue;   // skip duplicates (input is sorted)
        path.add(nums[i]);
        backtrack(i + 1, path);                      // pass i instead of i + 1 to allow reuse
        path.remove(path.size() - 1);
    }
}
```

**Tricks I learned:**
-

---

## Graphs
**Look for:** grids, dependencies, connected components, shortest paths in unweighted graphs.

```java
int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};   // grid DFS (Number of Islands)
void dfs(char[][] g, int r, int c) {
    if (r < 0 || c < 0 || r >= g.length || c >= g[0].length || g[r][c] != '1') return;
    g[r][c] = '0';
    for (int[] d : dirs) dfs(g, r + d[0], c + d[1]);
}
```
```java
// topological sort (Kahn): Course Schedule
int[] indeg = new int[n];
List<List<Integer>> adj = new ArrayList<>();
for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
for (int[] e : edges) { adj.get(e[1]).add(e[0]); indeg[e[0]]++; }
Queue<Integer> q = new ArrayDeque<>();
for (int i = 0; i < n; i++) if (indeg[i] == 0) q.offer(i);
int seen = 0;
while (!q.isEmpty()) {
    int u = q.poll(); seen++;
    for (int v : adj.get(u)) if (--indeg[v] == 0) q.offer(v);
}
return seen == n;                                    // false means there is a cycle
```
```java
int find(int x) { return parent[x] == x ? x : (parent[x] = find(parent[x])); }   // union-find
boolean union(int a, int b) { int ra = find(a), rb = find(b); if (ra == rb) return false; parent[ra] = rb; return true; }
```
- Multi-source BFS (Rotting Oranges): put every source in the queue before the loop starts.

**Tricks I learned:**
-

---

## Advanced Graphs
```java
// Dijkstra: Network Delay Time
PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);  // {node, dist}
pq.offer(new int[] {src, 0});
int[] dist = new int[n]; Arrays.fill(dist, Integer.MAX_VALUE); dist[src] = 0;
while (!pq.isEmpty()) {
    int[] cur = pq.poll();
    if (cur[1] > dist[cur[0]]) continue;             // stale entry
    for (int[] e : adj.get(cur[0])) {                // e = {to, weight}
        int nd = cur[1] + e[1];
        if (nd < dist[e[0]]) { dist[e[0]] = nd; pq.offer(new int[] {e[0], nd}); }
    }
}
```
- Minimum spanning tree: Prim (a heap) or Kruskal (sorted edges plus union-find).
- "At most k stops": Bellman-Ford with k + 1 rounds.

**Tricks I learned:**
-

---

## Dynamic Programming
**Look for:** "number of ways", "min / max cost", "is it possible", where a choice at each step depends on earlier results.

Steps: 1) define the state `dp[i]` in words, 2) write the recurrence, 3) set the base cases, 4) pick the iteration order, 5) optimize the space.

```java
// 1-D: House Robber
int prev2 = 0, prev1 = 0;
for (int x : nums) { int cur = Math.max(prev1, prev2 + x); prev2 = prev1; prev1 = cur; }
return prev1;
```
```java
// 2-D: Longest Common Subsequence
int[][] dp = new int[m + 1][n + 1];
for (int i = 1; i <= m; i++)
    for (int j = 1; j <= n; j++)
        dp[i][j] = a.charAt(i - 1) == b.charAt(j - 1)
                ? dp[i - 1][j - 1] + 1
                : Math.max(dp[i - 1][j], dp[i][j - 1]);
```
- Unbounded knapsack (Coin Change): loop over amounts on the outside and coins on the inside.
- Counting combinations (Coin Change II): loop over coins on the outside so each combination is counted once.

**Tricks I learned:**
-

---

## Greedy
- Maximum Subarray (Kadane): `cur = Math.max(x, cur + x); best = Math.max(best, cur);`
- Jump Game: track the farthest index you can reach.

**Tricks I learned:**
-

---

## Intervals
```java
Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));    // sort by start
List<int[]> merged = new ArrayList<>();
for (int[] in : intervals) {
    if (merged.isEmpty() || merged.get(merged.size() - 1)[1] < in[0]) merged.add(in);
    else merged.get(merged.size() - 1)[1] = Math.max(merged.get(merged.size() - 1)[1], in[1]);
}
```
- Meeting Rooms II: sort the start times and end times separately and sweep with two pointers, or use a min-heap of end times.

**Tricks I learned:**
-

---

## Bit Manipulation
- `x & (x - 1)` clears the lowest set bit; `x & -x` keeps only the lowest set bit.
- XOR of a number with itself is 0, which is how Single Number and Missing Number work.
- Use `>>>` (unsigned shift) for bit problems in Java.

**Tricks I learned:**
-

---

## Before I start coding (in the interview)
1. Ask about the input: size, negative numbers, duplicates, whether it can be empty, whether it is sorted.
2. Walk through an example by hand.
3. Describe the brute force and its complexity.
4. Name the pattern and optimize.
5. Write the code while explaining what I am doing.
6. Test it on the example and on edge cases, then state the final complexity.
