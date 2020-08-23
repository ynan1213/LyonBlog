package com.sqsoft.shiro;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.sqsoft.dao.UserMapper;
import com.sqsoft.entity.Module;
import com.sqsoft.entity.Role;
import com.sqsoft.entity.User;

/**
 * 认证授权工具类
 *
 */
public class AuthRealm extends AuthorizingRealm{

	@Autowired
	private UserMapper userMapper;
	
	//授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		User user = (User)principals.fromRealm(getName()).iterator().next();
		List<String> permissions=new ArrayList<String>();
        Set<Role> roles = user.getRoles();
       
        if(roles.size()>0) {
            for(Role role : roles) {
                Set<Module> modules = role.getModules();
                if(modules.size()>0) {
                    for(Module module : modules) {
                        permissions.add(module.getMname());
                    }
                }
            }
        }
        
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        info.addStringPermissions(permissions);
		return info;
	}
	
	//认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		
		UsernamePasswordToken utoken = (UsernamePasswordToken)token;
		String username = utoken.getUsername();
		User user = userMapper.queryUserName(username);
		
		if(user == null) {
			throw new UnknownAccountException("用户名不存在");
		}
		ByteSource byteSource = ByteSource.Util.bytes(username);
		AuthenticationInfo info = new SimpleAuthenticationInfo(user,user.getPassword(),byteSource,getName());
		return info;
	}
	
	public static void main(String[] args) {
		SimpleHash simpleHash = new SimpleHash("MD5", "123456", ByteSource.Util.bytes("zhangsan"), 1024);
		System.out.println(simpleHash.toString());
	}
}
