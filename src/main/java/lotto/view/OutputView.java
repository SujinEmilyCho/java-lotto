package lotto.view;

import lotto.view.dto.LottoTicketDTO;
import lotto.view.dto.ResultDTO;

import java.util.List;

public class OutputView {
    private static final String MESSAGE_FOR_LOTTO_TICKET_NUMBER = "%d개를 구매했습니다.";
    private static final String MESSAGE_FOR_LOTTO_TICKET_INFO = "[%d, %d, %d, %d, %d, %d]";
    private static final String MESSAGE_FOR_RESULT_ANNOUNCE = "당첨 통계\n---------";
    private static final String MESSAGE_FOR_BONUS_CASE = "%d개 일치, 보너스 볼 일치(%d원) - %d개";
    private static final String MESSAGE_FOR_DEFAULT_CASE = "%d개 일치 (%d원)- %d개";
    private static final String SECOND = "SECOND";
    private static final String MESSAGE_FOR_RETURN_RATE = "총 수익률은 %.1f입니다.";

    public static void printLottoTickets(List<LottoTicketDTO> lottoTicketDTOS) {
        System.out.println(String.format(MESSAGE_FOR_LOTTO_TICKET_NUMBER, lottoTicketDTOS.size()));
        for (LottoTicketDTO dto : lottoTicketDTOS) {
            System.out.println(String.format(MESSAGE_FOR_LOTTO_TICKET_INFO, dto.getNumbers()));
        }
        System.out.println();
    }

    public static void printResult(ResultDTO resultDTO) {
        System.out.println(MESSAGE_FOR_RESULT_ANNOUNCE);
        for (int i = 0; i < resultDTO.size(); i++) {
            int matchCount = resultDTO.getMatchCount(i);
            int defaultPrize = resultDTO.getDefaultPrize(i);
            int matchTicketCount = resultDTO.getMatchTicketCount(i);
            String message = String.format(findMessage(resultDTO.getName(i)), matchCount, defaultPrize, matchTicketCount);
            System.out.println(message);
        }
        System.out.println(String.format(MESSAGE_FOR_RETURN_RATE, resultDTO.getRate()));
    }

    private static String findMessage(String name) {
        if (isSecond(name)) {
            return MESSAGE_FOR_BONUS_CASE;
        }
        return MESSAGE_FOR_DEFAULT_CASE;
    }

    private static boolean isSecond(String name) {
        return SECOND.equals(name);
    }

}
