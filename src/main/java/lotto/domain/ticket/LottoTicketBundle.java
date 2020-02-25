package lotto.domain.ticket;

import lotto.domain.result.LottoMatchResultBundle;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class LottoTicketBundle {
    private final List<LottoTicket> lottoTickets;

    public LottoTicketBundle(List<LottoTicket> lottoTickets) {
        this.lottoTickets = lottoTickets;
    }

    public LottoMatchResultBundle createLottoMatchResultBundle(WinLottoTicket winLottoTicket) {
        return lottoTickets.stream()
                .map(winLottoTicket::createLottoResult)
                .collect(Collectors.collectingAndThen(Collectors.toList(), LottoMatchResultBundle::new));
    }

    public List<LottoTicket> getLottoTickets() {
        return Collections.unmodifiableList(this.lottoTickets);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LottoTicketBundle that = (LottoTicketBundle) o;
        return Objects.equals(lottoTickets, that.lottoTickets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lottoTickets);
    }
}
