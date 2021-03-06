package lotto.domain.ticket;

import lotto.domain.ticket.ball.LottoBall;
import lotto.domain.ticket.ball.LottoBallFactory;
import lotto.util.NullOrEmptyValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static lotto.domain.ticket.LottoTicket.LOTTO_PRICE;

public abstract class LottoMachine {
    private static final String MESSAGE_FOR_NOT_ENOUGH_MONEY = "%d는 최소 구매 금액보다 작습니다.";

    public final List<LottoTicket> buyTickets(int bettingMoney) {
        validateMoney(bettingMoney);

        int numberOfTickets = calculateAffordableNumber(bettingMoney);

        List<LottoTicket> lottoTickets = new ArrayList<>();
        for (int i = 0; i < numberOfTickets; i++) {
            lottoTickets.add(createOneTicket());
        }

        return lottoTickets;
    }

    private void validateMoney(int bettingMoney) {
        if (bettingMoney < LOTTO_PRICE) {
            throw new IllegalArgumentException(String.format(MESSAGE_FOR_NOT_ENOUGH_MONEY, bettingMoney));
        }
    }

    private int calculateAffordableNumber(int bettingMoney) {
        return bettingMoney / LOTTO_PRICE;
    }

    public abstract LottoTicket createOneTicket();

    public final WinLottoTicket createWinLottoTicket(List<Integer> winNumbers, int bonusNumber) {
        NullOrEmptyValidator.isNullOrEmpty(winNumbers);

        Set<LottoBall> winBalls = winNumbers.stream()
                .map(LottoBallFactory::getLottoBallByNumber)
                .collect(Collectors.toSet());

        LottoBall bonusBall = LottoBallFactory.getLottoBallByNumber(bonusNumber);

        return new WinLottoTicket(new LottoTicket(winBalls), bonusBall);
    }
}
