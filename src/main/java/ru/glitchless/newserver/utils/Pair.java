package ru.glitchless.newserver.utils;

public class Pair<F, S> {
    private final F first;
    private final S second;

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final Pair<?, ?> pair = (Pair<?, ?>) obj;

        if (first != null) {
            if (!first.equals(pair.first)) {
                return false;
            }
        } else {
            if (pair.first != null) {
                return false;
            }
        }

        if (second != null) {
            if (!second.equals(pair.second)) {
                return false;
            }
        } else {
            if (pair.second != null) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;

        if (first != null) {
            result = first.hashCode();
        }

        if (second != null) {
            result = Constants.MAGIC_NUMBER * result + second.hashCode();
        } else {
            result = Constants.MAGIC_NUMBER * result;
        }

        return result;
    }
}
