package ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class Test {
	public static void main(String[] args) {
		CacheManager cm = CacheManager.create("../../src/eh.xml");
		
		Cache cache = cm.getCache("categoryCache");
		
		cache.put(new Element("yuan1","nan1"));
		cache.put(new Element("yuan2","nan2"));
		cache.put(new Element("yuan3","nan3"));
		cache.put(new Element("yuan4","nan4"));
		
		Element el1 = cache.get("yuan1");
		
		String key = (String)el1.getObjectKey();
		String value = (String)el1.getObjectValue();
		
		System.out.println(key+"===="+value);
	}
}
