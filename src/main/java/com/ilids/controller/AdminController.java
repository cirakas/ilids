package com.ilids.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ilids.domain.Role;
import com.ilids.domain.User;
import com.ilids.service.RoleService;
import com.ilids.service.UserService;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Resource(name = "sessionRegistry")
    private SessionRegistryImpl sessionRegistry;

    @ModelAttribute("users")
    public List<User> getUsers() {
        return userService.getAllUsersExceptAdmin();
    }

    @ModelAttribute("roles")
    public List<Role> getRoles() {
        return roleService.getAllRoles();
    }

    @ModelAttribute("rolesWithoutRestrictedOnes")
    public List<Role> getRoles2() {
        return roleService.getAllRolesExceptRestrictedOnes();
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminwelcome(Model model) {
        return "/admin/users";
    }

    @RequestMapping(value = "/admin/enabled", method = RequestMethod.POST)
    public String enabledchange(@RequestParam Boolean enabled, @RequestParam Long userid, Model model) {
        userService.changeEnabled(userid, enabled);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/admin/addrole", method = RequestMethod.POST)
    public String adminchange(@RequestParam Long roleid, @RequestParam Long userid, Model model) {
        userService.addRoleToUser(roleid, userid);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/admin/removerole", method = RequestMethod.POST)
    public String adminchange2(@RequestParam Long roleid, @RequestParam Long userid, Model model) {
        userService.takeRoleFromUser(roleid, userid);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/admin/removeuser", method = RequestMethod.POST)
    public String removeuser(@RequestParam Long userid, Model model) {
        userService.removeUserFromDatabase(userid);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/admin/createorremoverole/", method = RequestMethod.POST)
    public String createOrRemoveRole(@RequestParam("newrole") String newRole, @RequestParam("action") String action,
            @RequestParam("rolename") String roleName) {
        if (action.equals("add")) {
            roleService.createNewRole(newRole);
        } else {
            roleService.delete(roleName);
        }
        return "redirect:/admin";
    }

    @RequestMapping(value = "/admin/invalidate/{name}", method = RequestMethod.GET)
    public String invalidate(@PathVariable String name) {
        List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
        for (Object o : allPrincipals) {
            if (((org.springframework.security.core.userdetails.User) o).getUsername().equals(name)) {
                List<SessionInformation> allSessions = sessionRegistry.getAllSessions(o, false);
                for (SessionInformation si : allSessions) {
                    si.expireNow();
                }
                break;

            }
        }
        return "redirect:/welcome";
    }
}
