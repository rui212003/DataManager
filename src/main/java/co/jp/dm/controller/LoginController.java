package co.jp.dm.controller;

import co.jp.dm.entity.User;
import co.jp.dm.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * Created by Rui on 2018/01/09.
 */

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String login(HttpSession session){
        User user = (User) session.getAttribute("user");
        session.setAttribute("imageRoot","http://storage.googleapis.com/valdacconstruction/");
        if(user != null){
            return "redirect:/";
        } else {
            return Config.LoginSession;
        }
    }

    /**
     * ユーザ名とパスワードにより、ログイン
     *
     * @param userName ユーザ名
     * @param password パスワード
     *
     * @return String　ユーザ名とパスワードは正しい場合はIndex画面へ遷移、
     * 　　　　　　　　　間違えた場合は、ログイン画面に戻す
     * */
    @RequestMapping(method = RequestMethod.POST)
    public String loginByUserid(@RequestParam("userName")String userName,
                                @RequestParam("password")String password,
                                HttpSession session){
        //password暗号化 SHA256でハッシュ
        String passwordSHA= DigestUtils.sha256Hex(password);
        User user = userService.getUserByUseridAndPassword(userName,passwordSHA);
        session.setAttribute("imageRoot","http://storage.googleapis.com/valdacconstruction/");
        if(user == null){
            session.setAttribute("message","ユーザ名またはパスワードが間違えました");
            return Config.LoginSession;
        } else {
            session.setAttribute("message",null);
            session.setAttribute("user",user);
            return "redirect:/";
        }
    }
}
