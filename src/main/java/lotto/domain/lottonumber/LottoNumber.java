package lotto.domain.lottonumber;

import java.util.Objects;

public class LottoNumber implements Comparable<LottoNumber> {
    public static final int FIRST_NUMBER = 1;
    public static final int LAST_NUMBER = 45;
    private static final String ERROR_OUT_OF_SCOPE_MESSAGE
            = "로또 숫자의 범위는 " + FIRST_NUMBER + "부터 " + LAST_NUMBER + " 사이입니다.";
    private final int number;

    private LottoNumber(int number) {
        this.number = number;
    }

    static LottoNumber of(int number) {
        if (isOutOfScope(number)) {
            throw new InvalidLottoNumberException(ERROR_OUT_OF_SCOPE_MESSAGE);
        }

        return new LottoNumber(number);
    }

    private static boolean isOutOfScope(int number) {
        return number < FIRST_NUMBER || number > LAST_NUMBER;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public int compareTo(LottoNumber o) {
        return this.number - o.number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final LottoNumber that = (LottoNumber) o;
        return number == that.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public String toString() {
        return String.valueOf(number);
    }
}