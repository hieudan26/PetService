package petservice.model.payload.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class SuccessResponseWithPagination {

    private Boolean success;
    private int status;
    private String message;
    private Map<String,Object> data;
    private PaginationResponse pagination;

    public SuccessResponseWithPagination(Page page){
        this.data = new HashMap<>();
        this.pagination = new PaginationResponse(page);
    }
    public SuccessResponseWithPagination(){
        this.data = new HashMap<>();
    }
}
