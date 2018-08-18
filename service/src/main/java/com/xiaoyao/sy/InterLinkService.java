package com.xiaoyao.sy;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import sys.Log;
import sys.ServiceException;

import java.util.List;

@Component
public class InterLinkService {

	@Autowired
	private InterLinkDao interLinkDao;

	public InterLink findOne(String id){
		return interLinkDao.findById(id).orElse(null);
	}

	public Page<InterLink> findAll(Pageable pageable){
		return interLinkDao.findAll(pageable);
	}

	public void save(InterLink vo, String fileid){
		if(StringUtils.isBlank(vo.getSname())){
			throw new ServiceException("名称不能为空");
		}

		if(StringUtils.isBlank(vo.getSurl())){
			throw new ServiceException("链接不能为空");
		}


		InterLink interLink=new InterLink();
		if(StringUtils.isNotBlank(vo.getId())){
			interLink=this.findOne(vo.getId());
		}

		interLink.setSname(vo.getSname());
		interLink.setSurl(vo.getSurl());
		interLink.setIsort(0);
		interLinkDao.save(interLink);

	}


	public List<InterLink> findByProperty(InterLink vo,int limit){
		InterLink interLink =new InterLink();
		Example<InterLink> example=Example.of(vo);

		return interLinkDao.findAll(example, PageRequest.of(0,limit)).getContent();
	}


	public void delete(String id){
		try {
			interLinkDao.deleteById(id);
		}catch (EmptyResultDataAccessException e){
			Log.info("id为"+id+"记录不存在",this.getClass());
			throw new ServiceException("记录不存在");
		}
	}







}