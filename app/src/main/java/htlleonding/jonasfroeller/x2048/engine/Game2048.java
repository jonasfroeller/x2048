package htlleonding.jonasfroeller.x2048.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Game2048 {
    private final Random random = new Random();

    public GameState initializeBoard() {
        GameState state = new GameState();
        state = placeRandomTile(state);
        state = placeRandomTile(state);
        return state;
    }

    public GameState placeRandomTile(GameState state) {
        ArrayList<Pair<Integer, Integer>> emptyCells = new ArrayList<>();
        
        for (int i = 0; i < state.getBoard().size(); i++) {
            for (int j = 0; j < state.getBoard().get(i).size(); j++) {
                if (state.getBoard().get(i).get(j) == 0) {
                    emptyCells.add(new Pair<>(i, j));
                }
            }
        }

        if (emptyCells.isEmpty()) {
            return state;
        }

        Pair<Integer, Integer> position = emptyCells.get(random.nextInt(emptyCells.size()));
        int newValue = random.nextDouble() < 0.9 ? 2 : 4;

        List<List<Integer>> newBoard = new ArrayList<>();
        for (int i = 0; i < state.getBoard().size(); i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < state.getBoard().get(i).size(); j++) {
                if (i == position.first && j == position.second) {
                    row.add(newValue);
                } else {
                    row.add(state.getBoard().get(i).get(j));
                }
            }
            newBoard.add(row);
        }

        return new GameState(newBoard);
    }

    private List<Integer> merge(List<Integer> row) {
        List<Integer> nonZero = row.stream()
                .filter(value -> value != 0)
                .collect(Collectors.toList());
        
        ArrayList<Integer> merged = new ArrayList<>();
        int i = 0;
        
        while (i < nonZero.size()) {
            if (i + 1 < nonZero.size() && nonZero.get(i).equals(nonZero.get(i + 1))) {
                merged.add(nonZero.get(i) * 2);
                i += 2;
            } else {
                merged.add(nonZero.get(i));
                i++;
            }
        }

        while (merged.size() < row.size()) {
            merged.add(0);
        }

        return merged;
    }

    public GameState moveLeft(GameState state) {
        List<List<Integer>> newBoard = state.getBoard().stream()
                .map(this::merge)
                .collect(Collectors.toList());
        return new GameState(newBoard);
    }

    public GameState moveRight(GameState state) {
        List<List<Integer>> newBoard = state.getBoard().stream()
                .map(row -> {
                    List<Integer> reversed = new ArrayList<>(row);
                    java.util.Collections.reverse(reversed);
                    List<Integer> merged = merge(reversed);
                    java.util.Collections.reverse(merged);
                    return merged;
                })
                .collect(Collectors.toList());
        return new GameState(newBoard);
    }

    public GameState moveUp(GameState state) {
        List<List<Integer>> rotated = rotate(state.getBoard());
        List<List<Integer>> newBoard = rotated.stream()
                .map(this::merge)
                .collect(Collectors.toList());
        return new GameState(rotate(newBoard, 3));
    }

    public GameState moveDown(GameState state) {
        List<List<Integer>> rotated = rotate(state.getBoard());
        List<List<Integer>> newBoard = rotated.stream()
                .map(row -> {
                    List<Integer> reversed = new ArrayList<>(row);
                    java.util.Collections.reverse(reversed);
                    List<Integer> merged = merge(reversed);
                    java.util.Collections.reverse(merged);
                    return merged;
                })
                .collect(Collectors.toList());
        return new GameState(rotate(newBoard, 3));
    }

    private List<List<Integer>> rotate(List<List<Integer>> board) {
        return rotate(board, 1);
    }

    private List<List<Integer>> rotate(List<List<Integer>> board, int times) {
        List<List<Integer>> rotated = board;
        for (int t = 0; t < times % 4; t++) {
            List<List<Integer>> newRotated = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                List<Integer> newRow = new ArrayList<>();
                for (int j = 0; j < 4; j++) {
                    newRow.add(rotated.get(3 - j).get(i));
                }
                newRotated.add(newRow);
            }
            rotated = newRotated;
        }
        return rotated;
    }

    private static class Pair<F, S> {
        final F first;
        final S second;

        Pair(F first, S second) {
            this.first = first;
            this.second = second;
        }
    }
}