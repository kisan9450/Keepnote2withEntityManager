<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html lang="en">

<head>

<title>Keep-Board</title>

</head>
<body>

    <form action="add" modelAttribute="note" method="POST">

        <h6>Note Title:</h6>

        <input type="text" name="noteTitle">

        <h6>Content: </h6>

        <input type="text" name="noteContent">

        <h6>Status: </h6>

        <input type="text" name="noteStatus">

        <input type="submit" value="submit">

    </form>
    <br>
    <table style="border: 2px solid;">

                <tr>

                    <th>ID</th>

                    <th>Note Title</th>

                    <th>Note Content</th>

                    <th>Note Status</th>

                    <th>Date of Created</th>

                    <th>Action</th>

                </tr>
                <c:forEach var="note" items="${notes}">

                                <tr>

                                    <th>${note.getNoteId()}</th>

                                    <th>${note.getNoteTitle()}</th>

                                    <th>${note.getNoteContent()}</th>

                                    <th>${note.getNoteStatus()}</th>

                                    <th>${note.getCreatedAt()}</th>

                                    <th><a href="delete.html?noteId=${note.getNoteId()}"><button>Delete</button></a>/<a href="update.html?noteId=${note.getNoteId()}"><button>update</button></a></th>

                                </tr>

                            </c:forEach>

            </table>

</body>



</html>



