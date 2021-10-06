<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:page title="Dashboard">
    <jsp:attribute name="css">
        <!-- Custom styles for this template -->
        <link href="css/dashboard.css" rel="stylesheet">
    </jsp:attribute>
    <jsp:body>
        <div class="container-fluid">
            <div class="row">
                <t:admin-nav currentPage="dashboard" />
                <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
                    <h2>Cool stats will be here soon!</h2>
                    <div class="table-responsive">
                        <table class="table table-striped table-sm">
                        </table>
                    </div>
                </main>
            </div>
        </div>
    </jsp:body>
</t:page>
