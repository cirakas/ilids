package com.ilids.controller;

import com.ilids.IService.DataService;
import com.ilids.IService.DeviceService;
import com.ilids.IService.RoleService;
import com.ilids.IService.SystemSettingsService;
import com.ilids.IService.UserService;
import com.ilids.conf.ServerConfig;
import com.ilids.domain.Devices;
import com.ilids.domain.SystemSettings;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.session.SessionRegistryImpl;
import com.ilids.domain.User;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author cirakas
 */
@Controller
@SessionAttributes("menuIdList")
public class LoginController {

    @Resource(name = "sessionRegistry")
    private SessionRegistryImpl sessionRegistry;

    @Autowired
    private SystemSettingsService systemSettingsService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private DataService dataService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private UserService userService;

    /**
     *
     * @return
     */
    @ModelAttribute("deviceModel")
    public Devices getDevices() {
        return new Devices();
    }

    //Same method is used for login and home redirection
    /**
     *
     * @param model
     * @param session
     * @param status
     * @return
     */
    @RequestMapping("/options")
    public String welcome(Model model, HttpSession session, SessionStatus status) {
        ServerConfig.latestAlertsScheduleCheckTime = System.currentTimeMillis();
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Object> menuIdList = roleService.getAllMenuIds(user.getUsername());
        List<Long> slaveIdList = new ArrayList<Long>();
        List<Devices> deviceIdList = deviceService.getAllUsedDevices();
        model.addAttribute("deviceIdList", deviceIdList);
        model.addAttribute("menuIdList", menuIdList);
        model.addAttribute("users", sessionRegistry.getAllPrincipals());
        List<SystemSettings> systemSettingsList = systemSettingsService.getAllSystemSettings();
        SystemSettings systemSettings = new SystemSettings();
        if (systemSettingsList != null && systemSettingsList.size() > 0) {
            systemSettings = systemSettingsList.get(0);
        }
        model.addAttribute("SystemSettings", systemSettings);

        User loginUser = userService.getUserByUserName(user.getUsername());
        if (loginUser != null) {
            sessionRegistry.registerNewSession("loginUser", loginUser);
        }

        session.removeAttribute("selection");

//	Data phase1PowerFactor=dataService.getLatestPowerFactorValues(30);
//	Data phase2PowerFactor=dataService.getLatestPowerFactorValues(32);
//	Data phase3PowerFactor=dataService.getLatestPowerFactorValues(34);
//	model.addAttribute("phase1PowerFactor", phase1PowerFactor.getData());
//	model.addAttribute("phase2PowerFactor", phase2PowerFactor.getData());
//	model.addAttribute("phase3PowerFactor", phase3PowerFactor.getData());
        return "options";
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/getDataFromDB", method = RequestMethod.GET)
    public String getDataFromDB() {
        return "home";
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginForm(Model model, HttpServletResponse response, HttpServletRequest request) {
        Long slaveId = deviceService.getFirstDevice();
        model.addAttribute("firstSlaveId", slaveId);

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        Date dateobj = new Date();
        String forDate = df.format(dateobj);

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("amap")) {
                    cookie.setValue("6");
                    cookie.setMaxAge(60 * 60);
                    response.addCookie(cookie);

                } else {
                    Cookie amapCook = new Cookie("amap", "6");
                    amapCook.setMaxAge(60 * 60); //1 hour
                    response.addCookie(amapCook);

                }

                if (cookie.getName().equals("did")) {
                    cookie.setValue("7");
                    cookie.setMaxAge(60 * 60);
                    response.addCookie(cookie);

                } else {
                    Cookie deviceCook = new Cookie("did", "7");
                    deviceCook.setMaxAge(60 * 60);
                    response.addCookie(deviceCook);

                }

                if (cookie.getName().equals("fDate")) {
                    cookie.setValue(forDate);
                    cookie.setMaxAge(60 * 60);
                    response.addCookie(cookie);

                } else {
                    Cookie frDateCook = new Cookie("fDate", forDate);
                    frDateCook.setMaxAge(60 * 60); //1 hour
                    response.addCookie(frDateCook);

                }

                if (cookie.getName().equals("fHour")) {
                    cookie.setValue("00");
                    cookie.setMaxAge(60 * 60);
                    response.addCookie(cookie);

                } else {
                    Cookie frHrCook = new Cookie("fHour", "00");
                    frHrCook.setMaxAge(60 * 60);
                    response.addCookie(frHrCook);

                }

                if (cookie.getName().equals("fMin")) {
                    cookie.setValue("00");
                    cookie.setMaxAge(60 * 60);
                    response.addCookie(cookie);

                } else {
                    Cookie frMinCook = new Cookie("fMin", "00");
                    frMinCook.setMaxAge(60 * 60); //1 hour
                    response.addCookie(frMinCook);

                }

                if (cookie.getName().equals("toDt")) {
                    cookie.setValue(forDate);
                    cookie.setMaxAge(60 * 60);
                    response.addCookie(cookie);

                } else {
                    Cookie toDateCook = new Cookie("toDt", forDate);
                    toDateCook.setMaxAge(60 * 60);
                    response.addCookie(toDateCook);

                }

                if (cookie.getName().equals("toHr")) {
                    cookie.setValue("00");
                    cookie.setMaxAge(60 * 60);
                    response.addCookie(cookie);

                } else {
                    Cookie toHrCook = new Cookie("toHr", "23");
                    toHrCook.setMaxAge(60 * 60); //1 hour
                    response.addCookie(toHrCook);

                }

                if (cookie.getName().equals("toMin")) {
                    cookie.setValue("00");
                    cookie.setMaxAge(60 * 60);
                    response.addCookie(cookie);
                } else {
                    Cookie toMinCook = new Cookie("toMin", "59");
                    toMinCook.setMaxAge(60 * 60);
                    response.addCookie(toMinCook);

                }

                if (cookie.getName().equals("tamap")) {
                    cookie.setValue("2");
                    cookie.setMaxAge(60 * 60);
                    response.addCookie(cookie);

                } else {
                    Cookie amapCook = new Cookie("tamap", "2");
                    amapCook.setMaxAge(60 * 60); //1 hour
                    response.addCookie(amapCook);

                }

                if (cookie.getName().equals("tfDate")) {
                    cookie.setValue(forDate);
                    cookie.setMaxAge(60 * 60);
                    response.addCookie(cookie);

                } else {
                    Cookie frDateCook = new Cookie("tfDate", forDate);
                    frDateCook.setMaxAge(60 * 60); //1 hour
                    response.addCookie(frDateCook);

                }

                if (cookie.getName().equals("tfHour")) {
                    cookie.setValue("00");
                    cookie.setMaxAge(60 * 60);
                    response.addCookie(cookie);

                } else {
                    Cookie frHrCook = new Cookie("tfHour", "00");
                    frHrCook.setMaxAge(60 * 60);
                    response.addCookie(frHrCook);
                }

                if (cookie.getName().equals("tfMin")) {
                    cookie.setValue("00");
                    cookie.setMaxAge(60 * 60);
                    response.addCookie(cookie);

                } else {
                    Cookie frMinCook = new Cookie("tfMin", "00");
                    frMinCook.setMaxAge(60 * 60); //1 hour
                    response.addCookie(frMinCook);

                }

                if (cookie.getName().equals("ttoDt")) {
                    cookie.setValue(forDate);
                    cookie.setMaxAge(60 * 60);
                    response.addCookie(cookie);

                } else {
                    Cookie toDateCook = new Cookie("ttoDt", forDate);
                    toDateCook.setMaxAge(60 * 60);
                    response.addCookie(toDateCook);

                }

                if (cookie.getName().equals("ttoHr")) {
                    cookie.setValue("00");
                    cookie.setMaxAge(60 * 60);
                    response.addCookie(cookie);

                } else {
                    Cookie toHrCook = new Cookie("ttoHr", "23");
                    toHrCook.setMaxAge(60 * 60); //1 hour
                    response.addCookie(toHrCook);

                }

                if (cookie.getName().equals("ttoMin")) {
                    cookie.setValue("00");
                    cookie.setMaxAge(60 * 60);
                    response.addCookie(cookie);
                } else {
                    Cookie toMinCook = new Cookie("ttoMin", "59");
                    toMinCook.setMaxAge(60 * 60);
                    response.addCookie(toMinCook);

                }

            }
        } else {
            Cookie amapCook = new Cookie("amap", "6");
            response.addCookie(amapCook);
            amapCook.setMaxAge(60 * 60 * 24); //1 hour

            Cookie deviceCook = new Cookie("did", "7");
            response.addCookie(deviceCook);
            deviceCook.setMaxAge(60 * 60);

            Cookie frDateCook = new Cookie("fDate", forDate);
            response.addCookie(frDateCook);
            frDateCook.setMaxAge(60 * 60); //1 hour

            Cookie frHrCook = new Cookie("fHour", "00");
            frHrCook.setMaxAge(60 * 60);
            response.addCookie(frHrCook);

            Cookie frMinCook = new Cookie("fMin", "00");
            response.addCookie(frMinCook);
            frMinCook.setMaxAge(60 * 60); //1 hour

            Cookie toDateCook = new Cookie("toDt", forDate);
            response.addCookie(toDateCook);
            toDateCook.setMaxAge(60 * 60);

            Cookie toHrCook = new Cookie("toHr", "23");
            response.addCookie(toHrCook);
            toHrCook.setMaxAge(60 * 60); //1 hour

            Cookie toMinCook = new Cookie("toMin", "59");
            response.addCookie(toMinCook);
            toMinCook.setMaxAge(60 * 60);

        //cookie for transducer
            Cookie tamapCook = new Cookie("tamap", "2");
            response.addCookie(tamapCook);
            tamapCook.setMaxAge(60 * 60 * 24); //1 hour

            Cookie tfrDateCook = new Cookie("tfDate", forDate);
            response.addCookie(tfrDateCook);
            tfrDateCook.setMaxAge(60 * 60); //1 hour

            Cookie tfrHrCook = new Cookie("tfHour", "00");
            tfrHrCook.setMaxAge(60 * 60);
            response.addCookie(tfrHrCook);

            Cookie tfrMinCook = new Cookie("tfMin", "00");
            response.addCookie(tfrMinCook);
            tfrMinCook.setMaxAge(60 * 60); //1 hour

            Cookie ttoDateCook = new Cookie("ttoDt", forDate);
            response.addCookie(ttoDateCook);
            ttoDateCook.setMaxAge(60 * 60);

            Cookie ttoHrCook = new Cookie("ttoHr", "23");
            response.addCookie(toHrCook);
            ttoHrCook.setMaxAge(60 * 60); //1 hour

            Cookie ttoMinCook = new Cookie("ttoMin", "59");
            response.addCookie(ttoMinCook);
            ttoMinCook.setMaxAge(60 * 60);

        }
        return "login/login";
    }

//    @RequestMapping("/denied")
//    public String denied() {
//        return "denied";
//    }
    /**
     *
     * @param model
     * @param flash
     * @return
     */
    @RequestMapping(value = "/accessdenied", method = RequestMethod.GET)
    public String loginerror(ModelMap model, RedirectAttributes flash) {
        model.addAttribute("error", "true");
        flash.addFlashAttribute("ErrorMessage", "User name or Password incorrect!");
        return "redirect:/login";
    }

    /**
     *
     * @return
     */
    @RequestMapping("/error")
    public String error() {
        return "error";
    }

}
