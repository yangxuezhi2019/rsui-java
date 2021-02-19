package org.rs.utils;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.CompilationMXBean;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryManagerMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SystemInfoUtils {
	
	static final long MB = 1024;
	static final long minute = 60000;
	
	public static class RsThreadInfo{
		private Integer activeCount;
		private Integer peekCount;
		private Integer daemonCount;
		private Long totalStartedCount;
		private Integer deadCount;
		
		public Integer getActiveCount() {
			return activeCount;
		}
		public void setActiveCount(Integer activeCount) {
			this.activeCount = activeCount;
		}
		public Integer getPeekCount() {
			return peekCount;
		}
		public void setPeekCount(Integer peekCount) {
			this.peekCount = peekCount;
		}
		public Integer getDaemonCount() {
			return daemonCount;
		}
		public void setDaemonCount(Integer daemonCount) {
			this.daemonCount = daemonCount;
		}
		public Long getTotalStartedCount() {
			return totalStartedCount;
		}
		public void setTotalStartedCount(Long totalStartedCount) {
			this.totalStartedCount = totalStartedCount;
		}
		public Integer getDeadCount() {
			return deadCount;
		}
		public void setDeadCount(Integer deadCount) {
			this.deadCount = deadCount;
		}

		@Override
		public String toString() {

			StringBuilder sb = new StringBuilder();
			String lineSeparator = System.getProperty("line.separator");
			sb.append("线程信息：");
			sb.append(lineSeparator);
			sb.append("线程总数（被创建并执行过的线程总数）：");
			sb.append(totalStartedCount);
			sb.append(lineSeparator);
			sb.append("仍活动的线程总数：");
			sb.append(activeCount);
			sb.append(lineSeparator);
			sb.append("峰值线程总数：");
			sb.append(peekCount);
			sb.append(lineSeparator);
			sb.append("守护线程总数：");
			sb.append(daemonCount);
			sb.append(lineSeparator);
			sb.append("死锁线程总数：");
			sb.append(deadCount);
			sb.append(lineSeparator);
			return sb.toString();
		}
	}
	
	public static class RsGarbageCollectorInfo{
		
		private String name;
		private String poolName;
		private Long collectionCount;
		private Long collectionTime;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPoolName() {
			return poolName;
		}
		public void setPoolName(String poolName) {
			this.poolName = poolName;
		}
		public Long getCollectionCount() {
			return collectionCount;
		}
		public void setCollectionCount(Long collectionCount) {
			this.collectionCount = collectionCount;
		}
		public Long getCollectionTime() {
			return collectionTime;
		}
		public void setCollectionTime(Long collectionTime) {
			this.collectionTime = collectionTime;
		}
		
		@Override
		public String toString() {

			StringBuilder sb = new StringBuilder();
			String lineSeparator = System.getProperty("line.separator");
			sb.append("名称：");
			sb.append(name);
			sb.append(lineSeparator);
			sb.append("收集：");
			sb.append(collectionCount);
			sb.append(lineSeparator);
			sb.append("总花费时间：");
			sb.append(collectionTime);
			sb.append(lineSeparator);
			sb.append("内存区名称：");
			sb.append(poolName);
			sb.append(lineSeparator);
			return sb.toString();
		}
	}
	
	public static class RsMemory{
		
		private String name;
		private Long init;
		private Long used;
		private Long committed;
		private Long max;
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Long getInit() {
			return init;
		}
		public void setInit(Long init) {
			this.init = init;
		}
		public Long getUsed() {
			return used;
		}
		public void setUsed(Long used) {
			this.used = used;
		}
		public Long getCommitted() {
			return committed;
		}
		public void setCommitted(Long committed) {
			this.committed = committed;
		}
		public Long getMax() {
			return max;
		}
		public void setMax(Long max) {
			this.max = max;
		}

		@Override
		public String toString() {

			StringBuilder sb = new StringBuilder();
			String lineSeparator = System.getProperty("line.separator");
			sb.append("名称：");
			sb.append(name);
			sb.append(lineSeparator);
			sb.append("初始大小：");
			sb.append(init);
			sb.append(lineSeparator);
			sb.append("已用大小：");
			sb.append(used);
			sb.append(lineSeparator);
			sb.append("已提交大小：");
			sb.append(committed);
			sb.append(lineSeparator);
			sb.append("最大(上限)：");
			sb.append(max);
			sb.append(lineSeparator);
			return sb.toString();
		}
	}
	
	public static class RsMemoryManagerInfo{
		
		private String name;
		private List<String> poolNames;
		private String objectName;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public List<String> getPoolNames() {
			return poolNames;
		}
		public void setPoolNames(List<String> poolNames) {
			this.poolNames = poolNames;
		}
		public String getObjectName() {
			return objectName;
		}
		public void setObjectName(String objectName) {
			this.objectName = objectName;
		}
		
		@Override
		public String toString() {

			StringBuilder sb = new StringBuilder();
			String lineSeparator = System.getProperty("line.separator");
			sb.append("管理器名称：");
			sb.append(name);
			sb.append(lineSeparator);
			sb.append(poolNames.stream().map(item->{return item;}).collect(Collectors.joining("; ")));
			sb.append(lineSeparator);
			return sb.toString();
		}
	}
	
	public static class RsRuntimeInfo{
		
		private String name;
		private String specName;
		private String specVendor;
		private String specVersion;
		private Long startTime;
		private Long uptime;
		private String vmName;
		private String vmVendor;
		private String vmVersion;
		private Map<String, String> systemProperties;
		private List<String> inputArguments;
		private String classPath;
		private String bootClassPath;
		private String libraryPath;
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getSpecName() {
			return specName;
		}
		public void setSpecName(String specName) {
			this.specName = specName;
		}
		public String getSpecVendor() {
			return specVendor;
		}
		public void setSpecVendor(String specVendor) {
			this.specVendor = specVendor;
		}
		public String getSpecVersion() {
			return specVersion;
		}
		public void setSpecVersion(String specVersion) {
			this.specVersion = specVersion;
		}
		public Long getStartTime() {
			return startTime;
		}
		public void setStartTime(Long startTime) {
			this.startTime = startTime;
		}
		public Long getUptime() {
			return uptime;
		}
		public void setUptime(Long uptime) {
			this.uptime = uptime;
		}
		public String getVmName() {
			return vmName;
		}
		public void setVmName(String vmName) {
			this.vmName = vmName;
		}
		public String getVmVendor() {
			return vmVendor;
		}
		public void setVmVendor(String vmVendor) {
			this.vmVendor = vmVendor;
		}
		public String getVmVersion() {
			return vmVersion;
		}
		public void setVmVersion(String vmVersion) {
			this.vmVersion = vmVersion;
		}
		public Map<String, String> getSystemProperties() {
			return systemProperties;
		}
		public void setSystemProperties(Map<String, String> systemProperties) {
			this.systemProperties = systemProperties;
		}
		public List<String> getInputArguments() {
			return inputArguments;
		}
		public void setInputArguments(List<String> inputArguments) {
			this.inputArguments = inputArguments;
		}
		public String getClassPath() {
			return classPath;
		}
		public void setClassPath(String classPath) {
			this.classPath = classPath;
		}
		public String getBootClassPath() {
			return bootClassPath;
		}
		public void setBootClassPath(String bootClassPath) {
			this.bootClassPath = bootClassPath;
		}
		public String getLibraryPath() {
			return libraryPath;
		}
		public void setLibraryPath(String libraryPath) {
			this.libraryPath = libraryPath;
		}
		
		@Override
		public String toString() {

			StringBuilder sb = new StringBuilder();
			String lineSeparator = System.getProperty("line.separator");
			sb.append(lineSeparator);
			sb.append("进程号：");
			sb.append(name);
			sb.append(lineSeparator);
			sb.append("jvm规范名称：");
			sb.append(specName);
			sb.append(lineSeparator);
			sb.append("jvm规范运营商：");
			sb.append(specVendor);
			sb.append(lineSeparator);
			sb.append("jvm规范版本：");
			sb.append(specVersion);
			sb.append(lineSeparator);
			sb.append("jvm启动时间：");
			sb.append(startTime);
			sb.append(lineSeparator);
			sb.append("系统属性：");
			sb.append(systemProperties);
			sb.append(lineSeparator);
			sb.append("jvm名称：");
			sb.append(vmName);
			sb.append(lineSeparator);
			sb.append("jvm运营商：");
			sb.append(vmVendor);
			sb.append(lineSeparator);
			sb.append("jvm实现版本：");
			sb.append(vmVersion);
			sb.append(lineSeparator);
			sb.append("vm参数：");
			sb.append(inputArguments.stream().map(item->{return item;}).collect(Collectors.joining(" ")));
			sb.append(lineSeparator);
			sb.append("类路径：");
			sb.append(classPath);
			sb.append(lineSeparator);
			sb.append("引导类路径：");
			sb.append(bootClassPath);
			sb.append(lineSeparator);
			sb.append("库路径：");
			sb.append(libraryPath);
			sb.append(lineSeparator);
			return sb.toString();
		}
	}
	
	public static class RsClassLoadInfo{
		
		private Long totalLoadedCount;
		private Integer loadedCount;
		private Long unLoadedCount;
		public Long getTotalLoadedCount() {
			return totalLoadedCount;
		}
		public void setTotalLoadedCount(Long totalLoadedCount) {
			this.totalLoadedCount = totalLoadedCount;
		}
		public Integer getLoadedCount() {
			return loadedCount;
		}
		public void setLoadedCount(Integer loadedCount) {
			this.loadedCount = loadedCount;
		}
		public Long getUnLoadedCount() {
			return unLoadedCount;
		}
		public void setUnLoadedCount(Long unLoadedCount) {
			this.unLoadedCount = unLoadedCount;
		}

		@Override
		public String toString() {

			StringBuilder sb = new StringBuilder();
			String lineSeparator = System.getProperty("line.separator");
			sb.append(lineSeparator);
			sb.append("已加载类总数：");
			sb.append(totalLoadedCount);
			sb.append(lineSeparator);
			sb.append("已加载当前类：");
			sb.append(loadedCount);
			sb.append(lineSeparator);
			sb.append("已卸载类总数：");
			sb.append(unLoadedCount);
			sb.append(lineSeparator);
			return sb.toString();
		}
	}
	
	public static class RsCompilationInfo{
		
		private String name;
		private Long time;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Long getTime() {
			return time;
		}
		public void setTime(Long time) {
			this.time = time;
		}

		@Override
		public String toString() {

			StringBuilder sb = new StringBuilder();
			String lineSeparator = System.getProperty("line.separator");
			sb.append(lineSeparator);
			sb.append("JIT 编译器：");
			sb.append(name);
			sb.append(lineSeparator);
			sb.append("总编译时间：");
			sb.append(time);
			sb.append(lineSeparator);
			return sb.toString();
		}
	}
	
	public static class RsSystemInfo{
		
		/**
		 * 编译信息
		**/
		private RsCompilationInfo compilationInfo;
		/**
		 * 类加载信息
		**/
		private RsClassLoadInfo classLoadInfo;
		/**
		 * 运行时信息
		 * */
		private RsRuntimeInfo runtimeInfo;
		/**
		 * 内存管理器信息
		 * */
		//private Map<String,String> memoryManagerInfo;
		/**
		 * 垃圾回收信息
		 * */
		private List<RsGarbageCollectorInfo> grabageList;
		/**
		 * 内存管理器信息
		 * */
		private List<RsMemoryManagerInfo> memoryManagers;
		/**
		 * vm pool 内存信息
		 * */
		private List<RsMemory> memoryList;
		/**
		 * 线程信息
		 * */
		private RsThreadInfo threadInfo;
		
		public RsCompilationInfo getCompilationInfo() {
			return compilationInfo;
		}
		public void setCompilationInfo(RsCompilationInfo compilationInfo) {
			this.compilationInfo = compilationInfo;
		}
		public RsClassLoadInfo getClassLoadInfo() {
			return classLoadInfo;
		}
		public void setClassLoadInfo(RsClassLoadInfo classLoadInfo) {
			this.classLoadInfo = classLoadInfo;
		}
		public RsRuntimeInfo getRuntimeInfo() {
			return runtimeInfo;
		}
		public void setRuntimeInfo(RsRuntimeInfo runtimeInfo) {
			this.runtimeInfo = runtimeInfo;
		}
		public List<RsGarbageCollectorInfo> getGrabageList() {
			return grabageList;
		}
		public void setGrabageList(List<RsGarbageCollectorInfo> grabageList) {
			this.grabageList = grabageList;
		}
		public List<RsMemory> getMemoryList() {
			return memoryList;
		}
		public void setMemoryList(List<RsMemory> memoryList) {
			this.memoryList = memoryList;
		}
		public RsThreadInfo getThreadInfo() {
			return threadInfo;
		}
		public void setThreadInfo(RsThreadInfo threadInfo) {
			this.threadInfo = threadInfo;
		}
		public List<RsMemoryManagerInfo> getMemoryManagers() {
			return memoryManagers;
		}
		public void setMemoryManagers(List<RsMemoryManagerInfo> memoryManagers) {
			this.memoryManagers = memoryManagers;
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			String lineSeparator = System.getProperty("line.separator");
			sb.append("SystemInfo [");
			sb.append(lineSeparator);
			sb.append(compilationInfo);
			sb.append(classLoadInfo);
			sb.append(runtimeInfo);
			sb.append("垃圾收集器信息：");
			sb.append(lineSeparator);
			sb.append(grabageList.stream().map(item->{return item.toString();}).collect(Collectors.joining(lineSeparator)));
			sb.append(lineSeparator);
			sb.append("内存管理器信息：");
			sb.append(lineSeparator);
			sb.append(memoryManagers.stream().map(item->{return item.toString();}).collect(Collectors.joining(lineSeparator)));
			sb.append(lineSeparator);
			sb.append("内存区信息：");
			sb.append(lineSeparator);
			sb.append(memoryList.stream().map(item->{return item.toString();}).collect(Collectors.joining(lineSeparator)));
			sb.append(lineSeparator);
			sb.append(threadInfo);			
			sb.append(lineSeparator);
			sb.append("]");
			return sb.toString();
		}
	}
	
	public static RsSystemInfo getSystemInfo() {
		
		RsSystemInfo systemInfo = new RsSystemInfo();
		//systemInfo.setGrabageCollectorInfo(getGarbageCollectorInfo());
		
		setCompilationInfo(systemInfo);
		setClassLoadInfo(systemInfo);
		setRuntimeInfo(systemInfo);
		setMemoryInfo(systemInfo);
		setGrabageCollectorInfo(systemInfo);
		setThreadInfo(systemInfo);
		setMemoryManager(systemInfo);
		return systemInfo;
	}
	
	static void setCompilationInfo(RsSystemInfo systemInfo) {
		
		CompilationMXBean compilation = ManagementFactory.getCompilationMXBean();
		RsCompilationInfo info = new RsCompilationInfo();
		info.setName(compilation.getName());
		if(compilation.isCompilationTimeMonitoringSupported()){
			info.setTime(compilation.getTotalCompilationTime());
		}else {
			info.setTime(0L);
		}
		systemInfo.setCompilationInfo(info);
	}
	
	private static void setClassLoadInfo(RsSystemInfo systemInfo){
		
		ClassLoadingMXBean classLoad= ManagementFactory.getClassLoadingMXBean();
		RsClassLoadInfo info = new RsClassLoadInfo();
		info.setTotalLoadedCount(classLoad.getTotalLoadedClassCount());
		info.setLoadedCount(classLoad.getLoadedClassCount());
		info.setUnLoadedCount(classLoad.getUnloadedClassCount());
		systemInfo.setClassLoadInfo(info);
	}
	
	public static void setRuntimeInfo(RsSystemInfo systemInfo){
		
		RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
		RsRuntimeInfo info = new RsRuntimeInfo();
		
		info.setName(runtime.getName().split("@")[0]);
		info.setSpecName(runtime.getSpecName());
		info.setSpecVendor(runtime.getSpecVendor());
		info.setSpecVersion(runtime.getSpecVersion());
		
		info.setStartTime(runtime.getStartTime());
		info.setSystemProperties(runtime.getSystemProperties());
		
		info.setUptime(runtime.getUptime());
		info.setVmName(runtime.getVmName());
		info.setVmVendor(runtime.getVmVendor());
		info.setVmVersion(runtime.getVmVersion());
		info.setInputArguments(runtime.getInputArguments());
		
		info.setClassPath(runtime.getClassPath());
		info.setBootClassPath(runtime.getBootClassPath());
		info.setLibraryPath(runtime.getLibraryPath());
		systemInfo.setRuntimeInfo(info);
	}
	
	private static void setMemoryManager(RsSystemInfo systemInfo){
		
		List<MemoryManagerMXBean> managers = ManagementFactory.getMemoryManagerMXBeans();
		
		List<RsMemoryManagerInfo> list = new ArrayList<>();
		if(managers != null && !managers.isEmpty()){
			for(MemoryManagerMXBean manager : managers){
				
				RsMemoryManagerInfo info = new RsMemoryManagerInfo();
				info.setName(manager.getName());
				info.setPoolNames(Arrays.asList(manager.getMemoryPoolNames()));
				info.setObjectName(manager.getObjectName().toString());
				list.add(info);
			}
		}
		systemInfo.setMemoryManagers(list);
	}
	
	static void setGrabageCollectorInfo(RsSystemInfo systemInfo) {
		
		List<GarbageCollectorMXBean> garbages = ManagementFactory.getGarbageCollectorMXBeans();
		List<RsGarbageCollectorInfo> grabageList = new ArrayList<>(); 
		for(GarbageCollectorMXBean garbage : garbages){
			
			RsGarbageCollectorInfo info = new RsGarbageCollectorInfo();
			info.setName(garbage.getName());
			info.setCollectionCount(garbage.getCollectionCount());
			info.setCollectionTime(garbage.getCollectionTime());
			info.setPoolName(Arrays.deepToString(garbage.getMemoryPoolNames()));
			grabageList.add(info);
		}
		systemInfo.setGrabageList(grabageList);
	}
	
	public static void setMemoryInfo(RsSystemInfo systemInfo) {
		
		List<RsMemory> poolList = new ArrayList<>();
		MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
		MemoryUsage heapMemory = memoryBean.getHeapMemoryUsage();
		RsMemory memory = new RsMemory();
		memory.setName("HEAP");
		memory.setInit(heapMemory.getInit());
		memory.setMax(heapMemory.getMax());
		memory.setUsed(heapMemory.getUsed());
		memory.setCommitted(heapMemory.getCommitted());
		poolList.add(memory);
		
		MemoryUsage nonHeapMemory = memoryBean.getNonHeapMemoryUsage();
		memory = new RsMemory();
		memory.setName("NONHEAP");
		memory.setInit(nonHeapMemory.getInit());
		memory.setMax(nonHeapMemory.getMax());
		memory.setUsed(nonHeapMemory.getUsed());
		memory.setCommitted(nonHeapMemory.getCommitted());
		poolList.add(memory);
		
		
		List<MemoryPoolMXBean> pools = ManagementFactory.getMemoryPoolMXBeans();
		for( MemoryPoolMXBean item: pools ) {
			
			MemoryUsage usage = item.getUsage();
			memory = new RsMemory();
			memory.setName(item.getName());
			memory.setInit(usage.getInit());
			memory.setMax(usage.getMax());
			memory.setUsed(usage.getUsed());
			memory.setCommitted(usage.getCommitted());
			poolList.add(memory);
		}
		systemInfo.setMemoryList(poolList);
	}
	
	static void setThreadInfo(RsSystemInfo systemInfo) {
		
		ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
		RsThreadInfo info = new RsThreadInfo();
		info.setActiveCount(threadBean.getThreadCount());
		info.setDaemonCount(threadBean.getDaemonThreadCount());
		info.setPeekCount(threadBean.getPeakThreadCount());
		info.setTotalStartedCount(threadBean.getTotalStartedThreadCount());
		long[] deadlockedIds = threadBean.findDeadlockedThreads();
		if( deadlockedIds != null ) {
			info.setDeadCount(deadlockedIds.length);
		}else {
			info.setDeadCount(0);
		}
		
		
		/*long[] deadlockedIds = threadBean.findDeadlockedThreads();
		if(deadlockedIds != null && deadlockedIds.length > 0){
			ThreadInfo[] deadlockInfos = threadBean.getThreadInfo(deadlockedIds);
			System.out.println("死锁线程信息:");
			System.out.println("\t\t线程名称\t\t状态\t\t");
			for(ThreadInfo deadlockInfo : deadlockInfos){
				System.out.println("\t\t"+deadlockInfo.getThreadName()+"\t\t"+deadlockInfo.getThreadState()
						+"\t\t"+deadlockInfo.getBlockedTime()+"\t\t"+deadlockInfo.getWaitedTime()
						+"\t\t"+deadlockInfo.getStackTrace().toString());
			}
		}*/
		systemInfo.setThreadInfo(info);
	}
}
