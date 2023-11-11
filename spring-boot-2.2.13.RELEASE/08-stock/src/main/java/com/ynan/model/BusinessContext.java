package com.ynan.model;

import com.ynan.dictionary.StockProcessTypeEnum;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

/**
 * @author yuannan
 */
@Data
@Accessors(chain = true)
public class BusinessContext {

	/**
	 * 当前作业环节
	 */
	private StockProcessTypeEnum processType;
	/**
	 * 执行人
	 */
	private String userId;
	/**
	 * 仓库id
	 */
	private Long warehouseId;

	//-----------------------------------------------
	//--------------以下字段会在库存日志中保存-----------
	//-----------------------------------------------
	/**
	 * 关联单据id
	 */
	private Long docId;
	/**
	 * 单据明细id
	 */
	private Long docLineId;
	/**
	 * log指定docId
	 */
	private Long logSpecDocId;
	/**
	 * log指定docType
	 */
	private String logSpecDocType;

	private String docType;
	/**
	 * 单据编号
	 */
	private String docNo;

	/**
	 * 任务id
	 */
	private Long taskId;
	/**
	 * 任务编号
	 */
	private String taskNo;
	/**
	 * 任务明细id
	 */
	private Long taskDetailId;

	private String taskType;

	private Long proDocId;

	private String comment;

	private boolean ctrlNotifyGss = true;

	@Exclude
	private Map<String, Object> extParams = new HashMap<>();

	public void putParam(String key, Object value) {
		if (StringUtils.isNotEmpty(key)) {
			extParams.put(key, value);
		}
	}

	public Object getParam(String key) {
		return extParams.get(key);
	}

	public BusinessContext copy() {
		BusinessContext copy = new BusinessContext();
		copy.setWarehouseId(this.getWarehouseId());
		copy.setDocType(this.getDocType());
		copy.setDocNo(this.getDocNo());
		copy.setDocId(this.getDocId());
		copy.setUserId(this.getUserId());
		copy.setProcessType(this.getProcessType());
		copy.setProDocId(this.getProDocId());
		copy.setTaskType(this.getTaskType());
		copy.setComment(this.getComment());
		copy.setLogSpecDocId(this.getLogSpecDocId());
		copy.setTaskNo(this.getTaskNo());
		copy.setTaskDetailId(this.getTaskDetailId());
		copy.setTaskId(this.getTaskId());
		copy.setDocLineId(this.getDocLineId());
		copy.setLogSpecDocType(this.getLogSpecDocType());
		copy.setCtrlNotifyGss(this.isCtrlNotifyGss());
		Map<String, Object> newExptParams = new HashMap<>(this.getExtParams());
		copy.setExtParams(newExptParams);
		return copy;
	}


}
