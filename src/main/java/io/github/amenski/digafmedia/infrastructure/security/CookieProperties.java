package io.github.amenski.digafmedia.infrastructure.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "auth.cookies")
public class CookieProperties {
    
    private boolean enabled = true;
    private String accessName = "sid";
    private String refreshName = "sid_refresh";
    private boolean secure = true;
    private String sameSite = "Lax";
    private String domain = "";
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public String getAccessName() {
        return accessName;
    }
    
    public void setAccessName(String accessName) {
        this.accessName = accessName;
    }
    
    public String getRefreshName() {
        return refreshName;
    }
    
    public void setRefreshName(String refreshName) {
        this.refreshName = refreshName;
    }
    
    public boolean isSecure() {
        return secure;
    }
    
    public void setSecure(boolean secure) {
        this.secure = secure;
    }
    
    public String getSameSite() {
        return sameSite;
    }
    
    public void setSameSite(String sameSite) {
        this.sameSite = sameSite;
    }
    
    public String getDomain() {
        return domain;
    }
    
    public void setDomain(String domain) {
        this.domain = domain;
    }
}