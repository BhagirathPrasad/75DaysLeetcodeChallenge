class Solution {
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> result = new ArrayList<>();

        Set<String> dict = new HashSet<>(wordList);

        if (!dict.contains(endWord)) {
            return result;
        }

        Map<String, List<String>> parents = new HashMap<>();
        Set<String> current = new HashSet<>();
        current.add(beginWord);

        boolean found = false;

        while (!current.isEmpty() && !found) {
            dict.removeAll(current);
            Set<String> next = new HashSet<>();

            for (String word : current) {
                char[] chars = word.toCharArray();

                for (int i = 0; i < chars.length; i++) {
                    char original = chars[i];

                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == original) {
                            continue;
                        }

                        chars[i] = c;
                        String newWord = new String(chars);

                        if (dict.contains(newWord)) {
                            next.add(newWord);
                            parents.computeIfAbsent(newWord, k -> new ArrayList<>()).add(word);

                            if (newWord.equals(endWord)) {
                                found = true;
                            }
                        }
                    }

                    chars[i] = original;
                }
            }

            current = next;
        }

        if (found) {
            List<String> path = new ArrayList<>();
            path.add(endWord);
            buildPaths(endWord, beginWord, parents, path, result);
        }

        return result;
    }

    private void buildPaths(String word, String beginWord,
                            Map<String, List<String>> parents,
                            List<String> path,
                            List<List<String>> result) {

        if (word.equals(beginWord)) {
            List<String> temp = new ArrayList<>(path);
            Collections.reverse(temp);
            result.add(temp);
            return;
        }

        if (!parents.containsKey(word)) {
            return;
        }

        for (String parent : parents.get(word)) {
            path.add(parent);
            buildPaths(parent, beginWord, parents, path, result);
            path.remove(path.size() - 1);
        }
    }
}