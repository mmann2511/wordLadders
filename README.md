# Word Ladders (Doublets)

A Java implementation of the classic **Word Ladders** game invented by Lewis Carroll in the 1870s. Given a start word and an end word, the engine finds the shortest possible path between them by changing one letter at a time — where every intermediate step must also be a valid word.

---

## How It Works

Each "rung" of the ladder differs from the previous word by exactly one letter. The engine uses **breadth-first search (BFS)** to guarantee the shortest possible ladder between two words.

### Example
```
cat → cot → dot → dog  (shortest ladder)
clash → class → claws → clows → clown
```

---

## Example Usage

```java
WordLadderGame game = new Doublets(inputStream);

// Find the shortest word ladder
List<String> ladder = game.getMinLadder("cat", "dog");
// Returns: [cat, cot, dot, dog]

// Check ladder length
game.getWordCount("cat", "dog"); // 4
```

---

## Classes

### `WordLadderGame` (Interface)
Defines the full contract for calculating word ladders including finding minimum ladders and validating word connections.

### `Doublets` (Implementation)
Core engine that:
- Loads a lexicon from a provided `InputStream`
- Uses **BFS** to find the shortest word ladder between two words
- Handles cases where no ladder exists

---

## Provided Word Lists

Several word lists of varying sizes are included via `WordLists.jar`:

| List | Size |
|---|---|
| SOWPODS | 267,751 words |
| OWL | 167,964 words |
| Other smaller lists | Various |

---

## Features
- Finds the **minimum length** word ladder between any two same-length words
- Returns an empty list if no ladder exists
- Works with any provided word list
- Handles edge cases like identical start/end words

---

## Tech Stack
- Java
- Breadth-First Search (BFS)
- `java.util.HashSet` for lexicon storage
- `java.util.Queue`, `java.util.LinkedList`

---
