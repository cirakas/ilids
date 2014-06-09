<%@page import="org.springframework.security.core.userdetails.User"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
        <div class="row">
          <div class="col-lg-12">
            <h1>User Management</h1>
            <ol class="breadcrumb">
              <li class="active"><i class="fa fa-dashboard"></i> Users</li>
            </ol>
            <div class="alert alert-success alert-dismissable">
              <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
              Welcome to ILIDS
            </div>
          </div>
        </div><!-- /.row -->
<div class="row">
       
            <div class="table-responsive">
              <table class="table table-bordered table-hover table-striped tablesorter">
                <thead>
                  <tr>
                    <th>Page <i class="fa fa-sort"></i></th>
                    <th>Visits <i class="fa fa-sort"></i></th>
                    <th>% New Visits <i class="fa fa-sort"></i></th>
                    <th>Revenue <i class="fa fa-sort"></i></th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>/index.html</td>
                    <td>1265</td>
                    <td>32.3%</td>
                    <td>$321.33</td>
                  </tr>
                  <tr>
                    <td>/about.html</td>
                    <td>261</td>
                    <td>33.3%</td>
                    <td>$234.12</td>
                  </tr>
                  <tr>
                    <td>/sales.html</td>
                    <td>665</td>
                    <td>21.3%</td>
                    <td>$16.34</td>
                  </tr>
                  <tr>
                    <td>/blog.html</td>
                    <td>9516</td>
                    <td>89.3%</td>
                    <td>$1644.43</td>
                  </tr>
                  <tr>
                    <td>/404.html</td>
                    <td>23</td>
                    <td>34.3%</td>
                    <td>$23.52</td>
                  </tr>
                  <tr>
                    <td>/services.html</td>
                    <td>421</td>
                    <td>60.3%</td>
                    <td>$724.32</td>
                  </tr>
                  <tr>
                    <td>/blog/post.html</td>
                    <td>1233</td>
                    <td>93.2%</td>
                    <td>$126.34</td>
                  </tr>
                </tbody>
              </table>
          </div>
        </div><!-- /.row -->
        
        