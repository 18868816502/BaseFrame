package com.sly.baseframe.data;

public class UserModel {
    private int cardCount;//绑定银行卡张数
    private int channelId;//渠道id
    private String createTime;//添加时间
    private String del;//身份证
    private String email;//邮箱
    private String googleAccount;//谷歌账户
    private int id;//
    private String idCard;//身份证
    private String isBindBTCAddress;//是否绑定btc地址 y绑定，n未绑定
    private String isBindCard;//是否绑定银行卡
    private String isRealAuthen;//是否实名认证y/n
    private String isReceiveLoginMsg;//是否接收登录异常消息y/n
    private String loginArea;//地区
    private String loginTime;//登录时间
    private String mobile;//手机号
    private String password;//密码
    private String payPwd;//支付密码
    private String photo;//头像
    private String realName;//真实姓名
    private String salt;//
    private int score;//账户安全得分
    private String ss_id;//ss_id
    private String updateTime;//更新时间
    private String userName;//用户名
    private int userType;//用户类型0个人1企业
    private String user_token;//

    public int getCardCount() {
        return cardCount;
    }

    public void setCardCount(int cardCount) {
        this.cardCount = cardCount;
    }

    public String getIsBindBTCAddress() {
        return isBindBTCAddress;
    }

    public void setIsBindBTCAddress(String isBindBTCAddress) {
        this.isBindBTCAddress = isBindBTCAddress;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDel() {
        return del;
    }

    public void setDel(String del) {
        this.del = del;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGoogleAccount() {
        return googleAccount;
    }

    public void setGoogleAccount(String googleAccount) {
        this.googleAccount = googleAccount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getIsBindCard() {
        return isBindCard;
    }

    public void setIsBindCard(String isBindCard) {
        this.isBindCard = isBindCard;
    }

    public String getIsRealAuthen() {
        return isRealAuthen;
    }

    public void setIsRealAuthen(String isRealAuthen) {
        this.isRealAuthen = isRealAuthen;
    }

    public String getIsReceiveLoginMsg() {
        return isReceiveLoginMsg;
    }

    public void setIsReceiveLoginMsg(String isReceiveLoginMsg) {
        this.isReceiveLoginMsg = isReceiveLoginMsg;
    }

    public String getLoginArea() {
        return loginArea;
    }

    public void setLoginArea(String loginArea) {
        this.loginArea = loginArea;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPayPwd() {
        return payPwd;
    }

    public void setPayPwd(String payPwd) {
        this.payPwd = payPwd;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getSs_id() {
        return ss_id;
    }

    public void setSs_id(String ss_id) {
        this.ss_id = ss_id;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getUser_token() {
        return user_token;
    }

    public void setUser_token(String user_token) {
        this.user_token = user_token;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
