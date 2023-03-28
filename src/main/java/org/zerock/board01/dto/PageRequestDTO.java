package org.zerock.board01.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {

    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 10;

    private String type; //검색의 종류, t, c, w, tc, tw, twc

    private String keyword;

    //BoardRepository에서 Spring[]로 처리하기 때문에 type 이라는 문자열을 배열로 반환해주는 기능
    //페이징 처리를 위해 사용하는 Pageable타입을 반환하는 기능 추가
    public String[] getType(){
        if(type == null || type.isEmpty()){
            return null;
        }
        return type.split("");
    }

    public Pageable getPageable(String...props){
        return PageRequest.of(this.page-1, this.size, Sort.by(props).descending());
    }

    private String link;

    // 검색 조건과 페이징 조건 등을 문자열로 구성
    public String getLink(){
        if(link == null){
            StringBuilder builder = new StringBuilder();

            builder.append("page" + this.page);
            builder.append("&size" + this.size);
            if(type != null && type.length() > 0){
                builder.append("&type"+ type);
            }
            if(keyword != null){
                try {
                    builder.append("&keyword="+ URLEncoder.encode(keyword, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                }
            }
            link = builder.toString();
        }
        return link;
    }

}
