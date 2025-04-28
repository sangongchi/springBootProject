package org.example.springbootdemo.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class PageResponse<T> extends BaseResponse<List<T>> {
  /**
   * 总记录数
   */
  private Long total;

  /**
   * 每页大小
   */
  private Integer limit;

  /**
   * 当前页码
   */
  private Integer curPage;

  /**
   * 总页数
   */
  private Integer totalPages;
}