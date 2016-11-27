package navigationcontroller;

import account.Account;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 * Created by o_0 on 2016-11-03.
 */
@ManagedBean
@SessionScoped
public class NavigationController {

    private boolean renderContent;
    private boolean renderFriend;

    public boolean isRenderContent() {
        boolean tmp = renderContent;
        //renderContent = false;
        return tmp;
    }

    public void setRenderContent(boolean renderContent) {
        this.renderContent = renderContent;
    }

    public boolean isRenderFriend() {
        boolean tmp = renderFriend;
        //renderFriend = false;
        return tmp;
    }

    public void setRenderFriend(boolean renderFriend) {
        this.renderFriend = renderFriend;
    }

    private static final String indexPage = "index";
    private String currentPage = "index";

    public String moveToStart() {
        return "newHome";
    }

    public String moveToPage() {
        return "newHome";
    }

    @ManagedProperty("#{account}")
    private Account userAccount;

    public Account getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(Account userAccount) {
        this.userAccount = userAccount;
    }

    @PostConstruct
    public void init() {
        this.currentPage = "index";
        this.renderContent = true;
        this.renderFriend = true;
    }

    public String getPage() {
        return currentPage;
    }

    public void setPage(String page) {
        this.currentPage = page;
        //this.renderContent = true;
    }

    public String moveToSelection(String id) {
        if (userAccount.isLoggedin()) {
            if(id.equals("1")) {
                return "newHome";
            }else if(id.equals("2")){
                return "GetNewFriends";
            }else if(id.equals("4")){
                return "chatwindow";
            }else if(id.equals("6")){
                return "profileEdit";
            }
        }
        if(id.equals("5")){
            return "login";
        }else if(id.equals("8")){
            return "createuser";
        }else if(id.equals("9")){
            return "notifytest";
        } else if(id.equals("0")){
            return "index";
        }
        return "index";
    }

    public String getNotificationChannel() {
        return "ch" + userAccount.getUserId();
    }

    public String goToStart() {
        return indexPage;

    }


}
