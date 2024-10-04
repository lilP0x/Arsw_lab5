var app = (function () {
    // Variables privadas
    var authorName = "";  // Almacena el nombre del autor seleccionado
    var blueprints = [];  // Almacena los planos y su tamaño

    // Función privada para actualizar la tabla de planos
    var updateBlueprintsTable = function (blueprintsList) {
        var totalPoints = 0;
        $("#blueprintsTable tbody").empty();  // Limpia la tabla antes de actualizarla

        // Itera sobre la lista de blueprints y crea filas para la tabla
        blueprintsList.map(function (bp) {
            var row = "<tr><td>" + bp.name + "</td><td>" + bp.points + "</td></tr>";
            $("#blueprintsTable tbody").append(row);
            totalPoints += bp.points;  // Suma el número de puntos
        });

        // Actualiza el campo de total de puntos en el DOM
        $("#totalPoints").text(totalPoints);
    };

    // Función pública para cambiar el nombre del autor
    var setAuthorName = function (name) {
        authorName = name;
    };

    // Función pública para obtener y actualizar los planos del autor
    var getBlueprintsByAuthor = function () {
        // Llama a la función getBlueprintsByAuthor del módulo apimock
        apimock.getBlueprintsByAuthor(authorName, function (data) {
            // Mapea los datos a un nuevo formato
            blueprints = data.map(function (bp) {
                return {
                    name: bp.name,
                    points: bp.points.length  // Cuenta el número de puntos de cada plano
                };
            });
            updateBlueprintsTable(blueprints);  // Actualiza la tabla con los planos obtenidos
        });
    };

    // Vincula el evento click del botón de búsqueda
    $(document).ready(function () {
        $("#searchBtn").click(function () {
            var author = $("#authorName").val();  // Obtiene el nombre del autor ingresado
            setAuthorName(author);                 // Cambia el nombre del autor en el módulo
            getBlueprintsByAuthor();               // Busca los blueprints del autor
        });
    });

    return {
        // Operaciones públicas expuestas por el módulo
        setAuthorName: setAuthorName,
        getBlueprintsByAuthor: getBlueprintsByAuthor
    };
})();
