package com.geex.cmdb.oss.service.abstractd;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import com.geex.cmdb.common.utils.DateUtils;
import com.geex.cmdb.common.utils.StringUtils;
import com.geex.cmdb.oss.entity.UploadResult;
import com.geex.cmdb.oss.properties.OssProperties;
import com.geex.cmdb.oss.service.IOssStrategy;

import java.io.InputStream;

/**
 * 对象存储策略(支持七牛、阿里云、腾讯云、minio)
 *
 * @author Lion Li
 */
public abstract class AbstractOssStrategy implements IOssStrategy {

	protected OssProperties properties;

	public abstract void init(OssProperties properties);

	@Override
	public abstract void createBucket();

	@Override
	public abstract String getServiceType();

	public String getPath(String prefix, String suffix) {
		// 生成uuid
		String uuid = IdUtil.fastSimpleUUID();
		// 文件路径
		String path = DateUtils.datePath() + "/" + uuid;
		if (StringUtils.isNotBlank(prefix)) {
			path = prefix + "/" + path;
		}
		return path + suffix;
	}

	@Override
	public abstract UploadResult upload(byte[] data, String path, String contentType);

	@Override
	public abstract void delete(String path);

	@Override
	public UploadResult upload(InputStream inputStream, String path, String contentType) {
		byte[] data = IoUtil.readBytes(inputStream);
		return this.upload(data, path, contentType);
	}

	@Override
	public abstract UploadResult uploadSuffix(byte[] data, String suffix, String contentType);

	@Override
	public abstract UploadResult uploadSuffix(InputStream inputStream, String suffix, String contentType);

	public abstract String getEndpointLink();
}
