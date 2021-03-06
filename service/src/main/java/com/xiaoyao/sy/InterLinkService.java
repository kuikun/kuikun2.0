package com.xiaoyao.sy;

import com.xiaoyao.sys.BaseDao;
import com.xiaoyao.sys.BaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys.ServiceException;

import java.util.List;

@Service
@Transactional
public class InterLinkService extends BaseService<InterLink> {

	@Autowired
	private InterLinkDao interLinkDao;


	public List<InterLink> findAll(){
		return interLinkDao.findAll();
	}

	@Override
	protected BaseDao<InterLink, String> getBaseDao() {
		return interLinkDao;
	}

	public void save(InterLink vo){
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

		if(!vo.getSurl().startsWith("http")){
			vo.setSurl(new StringBuffer("http").append("://").append(vo.getSurl()).toString());
		}

		interLink.setSname(vo.getSname());
		interLink.setSurl(vo.getSurl());
		interLink.setIsort(0);
		interLinkDao.save(interLink);

	}










}
