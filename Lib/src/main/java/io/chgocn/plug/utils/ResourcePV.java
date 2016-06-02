package io.chgocn.plug.utils;

/**
 * PV lock resource count.
 * @version 1.0 2014-04-24
 * @author JerryLi (lijian@dzs.mobi)
 */
final public class ResourcePV {
	private int iCount=0;
	/** 
	 * constructor
	 * @param resourceCount rousource count.
	 */
	public ResourcePV(int resourceCount){
		this.iCount = resourceCount;
	}
	/**
	 * checked is exist.
	 * @return boolean.
	 */
	public boolean isExist(){
		synchronized(this){
			return iCount == 0;
		}
	}
	/**
	 * seize res.
	 * @return boolean.
	 */
	public boolean seizeRes(){
		synchronized(this){
			if (this.iCount > 0){
				iCount--;
				return true;
			}else
				return false;
		}
	}
	/**
	 * revert res.
	 */
	public void revert(){
		synchronized(this){
			iCount++;
		}
	}
}
