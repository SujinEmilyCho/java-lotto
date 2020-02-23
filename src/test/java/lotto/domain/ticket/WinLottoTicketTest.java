package lotto.domain.ticket;

import lotto.domain.result.LottoResult;
import lotto.domain.ticket.ball.LottoBall;
import lotto.domain.ticket.ball.LottoBallFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class WinLottoTicketTest {

    private static Stream<Arguments> numberProvider() {
        return Stream.of(
                Arguments.of(new int[]{11, 12, 13, 18, 19, 20}, new LottoResult(0, false)),
                Arguments.of(new int[]{1, 12, 13, 18, 19, 20}, new LottoResult(1, false)),
                Arguments.of(new int[]{1, 2, 13, 18, 19, 20}, new LottoResult(2, false)),
                Arguments.of(new int[]{1, 2, 3, 8, 9, 10}, new LottoResult(3, false)),
                Arguments.of(new int[]{1, 2, 3, 4, 9, 10}, new LottoResult(4, false)),
                Arguments.of(new int[]{1, 2, 3, 4, 5, 10}, new LottoResult(5, false)),
                Arguments.of(new int[]{1, 2, 3, 4, 5, 7}, new LottoResult(5, true)),
                Arguments.of(new int[]{1, 2, 3, 4, 5, 6}, new LottoResult(6, false))
        );
    }

    @DisplayName("우승 로또 티켓과 비교하기")
    @ParameterizedTest
    @MethodSource("numberProvider")
    void name(int[] numbers, LottoResult expectedResult) {
        //given
        Set<LottoBall> winBalls = aLottoBalls(1, 2, 3, 4, 5, 6);
        WinLottoTicket winLottoTicket = new WinLottoTicket(new LottoTicket(winBalls), LottoBallFactory.getLottoBallByNumber(7));

        LottoTicket buyLottoTicket = new LottoTicket(aLottoBalls(numbers));

        //when
        LottoResult lottoResult = winLottoTicket.createLottoResult(buyLottoTicket);

        //then
        assertThat(lottoResult).isEqualTo(expectedResult);
    }

    private Set<LottoBall> aLottoBalls(int... numbers) {
        return Arrays.stream(numbers)
                .mapToObj(LottoBallFactory::getLottoBallByNumber)
                .collect(Collectors.toSet());
    }
}