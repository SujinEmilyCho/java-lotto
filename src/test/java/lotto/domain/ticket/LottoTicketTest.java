package lotto.domain.ticket;

import lotto.domain.customer.Customer;
import lotto.domain.customer.PurchaseInfo;
import lotto.domain.ticket.ball.LottoBall;
import lotto.domain.ticket.ball.LottoBallFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LottoTicketTest {

    @DisplayName("예외 테스트: 중복 제외 6개가 아닌 숫자로 티켓을 생성하는 경우 Exception 발생")
    @Test
    void test1() {
        int[] ballNumbers = {1, 2, 3, 4, 5, 5};
        Set<LottoBall> lottoBalls = Arrays.stream(ballNumbers)
                .mapToObj(LottoBallFactory::getLottoBallByNumber)
                .collect(Collectors.toSet());
        int wrongSize = lottoBalls.size();

        assertThatThrownBy(() -> new LottoTicket(lottoBalls))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("로또 번호의 갯수가 %d개로 올바르지 않습니다.", wrongSize);
    }

    @DisplayName("로또 티켓이 지정 번호를 포함하는지 확인 테스트")
    @ParameterizedTest
    @CsvSource(value = {"1,true", "7,false"})
    void name(int number, boolean expectedResult) {
        LottoMachine manualMachine = new ManualLottoMachine();
        List<Integer> manualNumbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        Customer customer = new Customer(new PurchaseInfo(1000, 1), Arrays.asList(manualNumbers));

        List<LottoTicket> lottoTickets = manualMachine.buyTickets(customer);

        LottoBall lottoBall = LottoBallFactory.getLottoBallByNumber(number);

        assertThat(lottoTickets.get(0).has(lottoBall)).isEqualTo(expectedResult);
    }
}
