package lotto.domain.ticket.ball;

import java.util.Objects;

public final class LottoBall {
    public static final int LOWER_BOUND = 1;
    public static final int UPPER_BOUND = 45;
    public static final String MESSAGE_FOR_INVALID_LOTTO_BALL_NUMBER = "%d: 로또 범위 이외 숫자입니다";

    private final int number;

    LottoBall(int number) {
        validateNumber(number);
        this.number = number;
    }

    public static void validateNumber(int number) {
        if (number < LOWER_BOUND || number > UPPER_BOUND) {
            throw new IllegalArgumentException(String.format(MESSAGE_FOR_INVALID_LOTTO_BALL_NUMBER, number));
        }
    }

    public int getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LottoBall lottoBall = (LottoBall) o;
        return number == lottoBall.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public String toString() {
        return "LottoBall{" +
                "number=" + number +
                '}';
    }
}
