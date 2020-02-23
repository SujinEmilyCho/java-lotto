package lotto.domain.ticket;

import lotto.domain.ticket.ball.LottoBall;
import lotto.domain.ticket.ball.LottoBallFactory;
import lotto.view.dto.BettingMoneyDTO;
import lotto.view.dto.WinLottoTicketDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class LottoMachineTest {

    @DisplayName("가격에 해당하는 티켓을 발급하는지 확인")
    @ParameterizedTest
    @CsvSource(value = {"1000,1", "1500,1", "2000,2"})
    void test1(int money, int expect) {
        LottoMachine realMachine = new AutoLottoMachine();
        List<LottoTicket> lottoTickets = realMachine.buyTickets(new BettingMoneyDTO(money));

        assertThat(lottoTickets).hasSize(expect);
    }

    @DisplayName("우승 로또 만들기")
    @Test
    void makeWinLottoTicket() {
        //given
        LottoMachine lottoMachine = new AutoLottoMachine();
        Set<LottoBall> winBalls = aLottoBalls(1, 2, 3, 4, 5, 6);
        LottoBall bonusBall = LottoBallFactory.getLottoBallByNumber(7);
        WinLottoTicket expectedLotto = new WinLottoTicket(new LottoTicket(winBalls), bonusBall);

        WinLottoTicketDTO winLottoTicketDTO = new WinLottoTicketDTO("1,2,3,4,5,6", 7);

        //when
        WinLottoTicket winLottoTicket = lottoMachine.createWinLottoTicket(winLottoTicketDTO);

        //then
        assertThat(winLottoTicket).isEqualTo(expectedLotto);
    }

    @DisplayName("테스트용 고정 번호 로또 티켓 생성하기")
    @Test
    void test2() {
        //given
        LottoMachine testLottoMachine = new LottoMachineForTest();
        BettingMoneyDTO bettingMoneyDTO = new BettingMoneyDTO(1000);

        LottoTicket expectedTicket = new LottoTicket(aLottoBalls(1, 2, 3, 4, 5, 6));

        //when
        List<LottoTicket> tickets = testLottoMachine.buyTickets(bettingMoneyDTO);

        //then
        assertThat(tickets.get(0)).isEqualTo(expectedTicket);
    }

    private Set<LottoBall> aLottoBalls(int... numbers) {
        return Arrays.stream(numbers)
                .mapToObj(LottoBallFactory::getLottoBallByNumber)
                .collect(Collectors.toSet());
    }
}