package cn.wolves.common.frame.spring;

import java.io.Serializable;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author linwillen
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class PageQuery<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Page<T> pager;
	private T queryData;

}
