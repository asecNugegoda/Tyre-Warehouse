
<%@page import="com.myapp.util.SectionComparator"%>
<%@page import="com.myapp.util.ModuleComparator"%>
<%@page import="java.util.TreeSet"%>
<%@page import="com.myapp.login.bean.PageBean"%>
<%@page import="com.myapp.login.bean.ModuleBean"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>

<!--Banner-->
<div class="banner">

    <!-- App header begin -->
    <header>
        <a href='homeCall.action' class="brand"></a>
        <div class="service-status">
            <div id="node"></div><div id="serviceStatus" class="active"></div>
        </div>
        <div class="top-nav">
            <div class="right-links">
                <a href='logOut.action' id="logout"  class="lnk-logout" title="LogOut"></a>
                <a href='homeCall.action' id="home" class="lnk-home" title="Home"></a>
            </div>
            <div class="user-info">
                <h1>Welcome</h1>
                <div>
                    <a href="#">${SessionObject.userName}</a><br />
                    <span>${SessionObject.userRole}</span>
                </div>
            </div>
        </div>
    </header>
    <!-- End -->

</div>
<!--End of Banner-->

<!-- Left side navigation begin -->
<div class="left-nav">
    <ul class="navigation">

        <%
            try {
                System.out.println("server request " + request.getSession());
                HashMap<ModuleBean, List<PageBean>> sectionPageList = (HashMap<ModuleBean, List<PageBean>>) request.getSession().getAttribute("modulePageList");

                ModuleComparator sec1 = new ModuleComparator();
                Set<ModuleBean> section = new TreeSet<ModuleBean>(sec1);
                Set<ModuleBean> section1 = sectionPageList.keySet();
                for (ModuleBean sec2 : section1) {
                    section.add(sec2);
                }

                int secId = 1;
                int pageId = 1000;

                for (ModuleBean sec : section) {
                    out.println("<li class='parentnav'><a href='#' class='dropmenu do-nothing'><i class='fa fa-chevron-right' aria-hidden='true'></i>" + sec.getMODULE_NAME() + "</a>");
                    out.println("<ul id=\"" + secId + "\" class='sub-nav'>");

                    List<PageBean> pageList = sectionPageList.get(sec);
                    SectionComparator sectionCm = new SectionComparator();
                    Set<PageBean> sortPageList = new TreeSet<PageBean>(sectionCm);
                    for (PageBean sec2 : pageList) {
                        sortPageList.add(sec2);
                    }
                    for (PageBean pageBean : sortPageList) {
                        pageId++;
                        out.println("<li>");
                        out.println("<a id= " + pageId + " href=\'" + request.getContextPath() + "/" + pageBean.getPAGE_URL() + ".action\'>");
                        out.println("<i class='fa fa-angle-right' aria-hidden='true'></i>" + pageBean.getPAGE_NAME());
                        out.println("</a>");
                        out.println("</li>");
                    }

                    out.println(" </ul>");
                    secId++;
                }
                out.println("</a></li>");
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        %>

    </ul>
</div>
<!-- End -->

<script>

$(function () {

        $('#logout').click(function () {
            $.removeCookie('selectedpage', {path: '/'});
            window.location = $(this).attr('hrefa');
        });
        $('#home').click(function () {
            $.removeCookie('selectedpage', {path: '/'});
            window.location = $(this).attr('href');
        });

//        logout(){
//            var cookies = $.cookie();
//            for (var cookie in cookies) {
//                $.removeCookie(cookie);
//            }
//        }

        var idpage = $.cookie("selectedpage");
        console.log(" cookie----- " + idpage)
        var idpageval = "#" + idpage;
        $(idpageval).addClass('menuselect');
    })

    

</script>
<!--End of Left Menu-->


