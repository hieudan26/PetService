package petservice.model.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

@NoArgsConstructor
@Getter
@Setter
public class PaginationResponse {
    int _page;
    int _totalPage;
    int _limit;
    int _currentItem;
    long _totalItem;

    public PaginationResponse(int _page, int _totalPage, int _limit, int _currentItem, long _totalItem) {
        this._page = _page;
        this._totalPage = _totalPage;
        this._limit = _limit;
        this._currentItem = _currentItem;
        this._totalItem = _totalItem;
    }
    public PaginationResponse(Page pageItem) {
        this._page = pageItem.getNumber();
        this._totalPage = pageItem.getTotalPages();
        this._limit = pageItem.getSize();
        this._currentItem = pageItem.getContent().size();
        this._totalItem = pageItem.getTotalElements();
    }
}
