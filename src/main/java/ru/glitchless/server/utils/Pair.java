package ru.glitchless.server.utils;

public class Pair<F, S> {
    private F first;
    private S second;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Pair<?, ?> pair = (Pair<?, ?>) o;

        return (first != null ? first.equals(pair.first) : pair.first == null)
                && (second != null ? second.equals(pair.second) : pair.second == null);
    }

    @Override
    public int hashCode() {
        int result = first != null ? first.hashCode() : 0;
        result = Constants.MAGIC_NUMBER * result + (second != null ? second.hashCode() : 0);
        return result;
    }
}
