package cn.wolves.common.frame.spring;

import cn.wolves.common.exception.BusinessException;
import cn.wolves.common.utils.ServletKit;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author linwillen
 */
@Slf4j
public abstract class BasePageApi extends BaseApi {
	
	@Autowired
	ObjectMapper objectMapper;

	/**
	 * 解析成查询参数和分页参数对象
	 * 
	 * @param clazz
	 * @return
	 */
	public <T> PageQuery<T> getQueryVo(Class<T> clazz) {
		try {
            HttpServletRequest request = ServletKit.getRequest();
            PageQuery<T> pageQuery = objectMapper.readValue(request.getInputStream(), new TypeReference<PageQuery<T>>() {
			});
			pageQuery.setQueryData(objectMapper.readValue(objectMapper.writeValueAsBytes(pageQuery.getQueryData()), clazz));
			return pageQuery;
		} catch (IOException e) {
			log.error("解析参数出现异常", e);
			throw new BusinessException("解析参数出现异常,请确认分页参数正确");
		}
	}

	public <T> IPage<T> getResultPage(Page<T> pagination, List<T> results) {
        IPage<T> page = new Page<T>();
		page.setSize(pagination.getSize());
		page.setCurrent(pagination.getCurrent());
		page.setTotal(pagination.getTotal());
		page.setRecords(results);
		return page;
	}

}
