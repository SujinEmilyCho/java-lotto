package lotto.view;

import lotto.exception.ConvertFailException;
import lotto.view.dto.BettingMoneyDTO;
import lotto.view.dto.ManualNumbersDTO;
import lotto.view.dto.NumberOfTicketDTO;
import lotto.view.dto.WinLottoTicketDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class InputView {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String INPUT_MONEY_MESSAGE = "구입 금액을 입력해 주세요.";
    private static final String INPUT_WINNING_NUMBER_MESSAGE = "지난 주 당첨 번호를 입력해 주세요.";
    private static final String INPUT_BONUS_NUMBER_MESSAGE = "보너스 볼을 입력해 주세요.";
    private static final String NULL_OR_EMPTY_INPUT_MESSAGE = "입력값이 비어있습니다.";
    private static final String INVALID_NUMBER_INPUT_MESSAGE = "잘못된 숫자 입력";
    private static final String PARSE_FAIL_EXCEPTION_MESSAGE = "%s : 숫자가 아닌 문자가 존재합니다.";
    private static final String COMMA = ",";
    private static final String INPUT_NUMBER_OF_MANUAL_LOTTO_MESSAGE = "\n수동으로 구매할 로또 수를 입력해 주세요.";
    private static final String INPUT_NUMBERS_FOR_MANUAL_LOTTO = "\n수동으로 구매할 번호를 입력해 주세요.";

    public static BettingMoneyDTO inputBettingMoney() {
        System.out.println(INPUT_MONEY_MESSAGE);
        String bettingMoney = scanner.nextLine();
        isNullOrEmpty(bettingMoney);

        return new BettingMoneyDTO(convertToInteger(bettingMoney));
    }

    public static NumberOfTicketDTO inputNumberOfManualLotto() {
        System.out.println(INPUT_NUMBER_OF_MANUAL_LOTTO_MESSAGE);
        String numberOfManualLotto = scanner.nextLine();
        isNullOrEmpty(numberOfManualLotto);

        return new NumberOfTicketDTO(convertToInteger(numberOfManualLotto));
    }

    public static ManualNumbersDTO inputManualNumbers(int numberOfManualLotto) {
        System.out.println(INPUT_NUMBERS_FOR_MANUAL_LOTTO);

        List<List<Integer>> manualNumbers = new ArrayList<>();
        for (int i = 0; i < numberOfManualLotto; i++) {
            String numbers = scanner.nextLine();
            isNullOrEmpty(numbers);
            manualNumbers.add(collectNumber(numbers));
        }

        return new ManualNumbersDTO(manualNumbers);
    }

    public static WinLottoTicketDTO inputWinLottoInfo() {
        List<Integer> winNumbers = inputWinningNumber();
        int bonusNumber = inputBonusNumber();
        return new WinLottoTicketDTO(winNumbers, bonusNumber);
    }

    private static List<Integer> inputWinningNumber() {
        System.out.println(INPUT_WINNING_NUMBER_MESSAGE);
        String winningNumber = scanner.nextLine();
        isNullOrEmpty(winningNumber);

        return collectNumber(winningNumber);
    }

    private static int inputBonusNumber() {
        System.out.println(INPUT_BONUS_NUMBER_MESSAGE);
        String bonusNumber = scanner.nextLine();
        isNullOrEmpty(bonusNumber);

        return convertToInteger(bonusNumber);
    }

    private static void isNullOrEmpty(String input) {
        if (input == null || (input.trim()).isEmpty()) {
            throw new IllegalArgumentException(NULL_OR_EMPTY_INPUT_MESSAGE);
        }
    }

    private static int convertToInteger(String input) {
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new ConvertFailException(INVALID_NUMBER_INPUT_MESSAGE);
        }
    }

    private static List<Integer> collectNumber(String winningNumbers) {
        try {
            return splitAndConvertToInteger(winningNumbers);
        } catch (NumberFormatException e) {
            throw new ConvertFailException(String.format(PARSE_FAIL_EXCEPTION_MESSAGE, winningNumbers));
        }
    }

    private static List<Integer> splitAndConvertToInteger(String winningNumbers) {
        return Arrays.stream(winningNumbers.split(COMMA))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}
