/**
 * Asignatura
 */
var formAsig = $("#formAsig");
var tablaAsig = $("#tablaAsig").DataTable({
    select: true
});
var btnDeleteAsig = document.querySelector("#deleteAsig");

$("#tablaAsig").parents('div.dataTables_wrapper').first().hide();

//Ver formulario
$("#ingresarAsig").on('click',function (){
    clearPanel();
    formAsig.removeClass("hide");
});

//Ver tabla
$("#verTablaAsig").on('click',function (){
   clearPanel();
   getAsig()
   $("#tablaContainerAsig").removeClass("hide");
    $("#tablaAsig").parents('div.dataTables_wrapper').first().show();
});

//Subir formulario de asignaturas
formAsig.submit(function (){
    saveAsig();
});

//Si se selecciona una fila de la tabla
$("#tablaAsig tbody").on('click','tr',function (){


    if($(this).hasClass('selected')){
        $(this).removeClass('selected');
        selectedRow = null;
        btnDeleteAsig.disabled = true;
    }
    else{
        tablaAsig.$('tr.selected').removeClass('selected');
        $(this).addClass('selected');
        btnDeleteAsig.disabled= false;
        selectedRow = tablaAsig.row(this).data();
        console.log(selectedRow)
    }

});

//eliminar asignatura
$("#deleteAsig").on('click',function (){
    if (selectedRow !==null){
        var codAsig = selectedRow[0];
        // Mostrar ventana de confirmación
        var confirmar = window.confirm('¿Estás seguro de que deseas eliminar a esta Asignatura?');

        // Si el usuario hace clic en "Aceptar", proceder con la eliminación
        if (confirmar) {
            deleteAsig(codAsig);
            selectedRow = null;
            btnDeleteAsig.disabled = true;
            tablaAsig.ajax.reload();
        }
    }
});
/**
 * Periodos
 *
 */
var formPeriodo = $("#formPeriodo");
var tablaPeriodo = $("#tablaPeriod").DataTable({
   select: true
});
var btnDeletePeriod = document.querySelector("#deletePeriod");

$("#tablaPeriod").parents('div.dataTables_wrapper').first().hide();

$("#ingresarPeriod").on('click',function (){
   clearPanel();
   formPeriodo.removeClass("hide");
});

$("#verTablaPeriod").on('click',function (){
    clearPanel();
    getPeriods();
    $("#tablaContainerPeriod").removeClass("hide");
    $("#tablaPeriod").parents('div.dataTables_wrapper').first().show();
});

//Guardar periodo
formPeriodo.submit( function (){
   savePeriod();
});
//Cargar tabla de estudiantes
var tableEst = $("#tablaEst").DataTable({
    select: true
});
$("#tablaEst").parents('div.dataTables_wrapper').first().hide();

var formEst = $("#formEstudiante");
var botonEliminarEst = document.querySelector("#eliminarEst");
var selectedRow = null;
//Si presiona ingresar estudiante
$("#inEstudiante").on('click',function(){

    clearPanel()

    $("#formEstudiante").removeClass("hide");
})

//Si presiona listar estudiantes


$("#listEstButton").on('click',function (){

    //Cerrar todos los paneles
    clearPanel()
    getStudents();
    //Abrir tabla
    $("#tablaContainer").removeClass("hide");
    $("#tablaEst").parents('div.dataTables_wrapper').first().show();
});

//Se sube el estudiante
formEst.submit(function (){
    guardarEstudiante();
});

//Si se selecciona una fila de la tabla
$("#tablaEst tbody").on('click','tr',function (){


    if($(this).hasClass('selected')){
        $(this).removeClass('selected');
        selectedRow = null;
        botonEliminarEst.disabled = true;
    }
    else{
        tableEst.$('tr.selected').removeClass('selected');
        $(this).addClass('selected');
        botonEliminarEst.disabled= false;
        selectedRow = tableEst.row(this).data();
        console.log(selectedRow)
    }

});

//Si se preciona eliminar
$("#eliminarEst").on('click',function (){

    if (selectedRow !==null){
        var matricula = selectedRow[0];
        console.log(matricula)
        // Mostrar ventana de confirmación
        var confirmar = window.confirm('¿Estás seguro de que deseas eliminar a este estudiante?');

        // Si el usuario hace clic en "Aceptar", proceder con la eliminación
        if (confirmar) {
            eliminarEstudiante(matricula);
            selectedRow = null;
            botonEliminarEst.disabled = true;
            tableEst.ajax.reload();
        }
    }

});
//Selecciona un periodo
$("#tablaPeriod tbody").on('click','tr',function (){


    if($(this).hasClass('selected')){
        $(this).removeClass('selected');
        selectedRow = null;
        btnDeletePeriod.disabled = true;
    }
    else{
        tablaPeriodo.$('tr.selected').removeClass('selected');
        $(this).addClass('selected');
        btnDeletePeriod.disabled= false;
        selectedRow = tablaPeriodo.row(this).data();
        console.log(selectedRow)
    }

});

//Eliminar periodo
$("#deletePeriod").on('click',function (){
    if (selectedRow !==null){
        var codPeriodo = selectedRow[0];
        console.log(codPeriodo)
        // Mostrar ventana de confirmación
        var confirmar = window.confirm('¿Estás seguro de que deseas eliminar a este periodo?');

        // Si el usuario hace clic en "Aceptar", proceder con la eliminación
        if (confirmar) {
            eliminarPeriodo(codPeriodo);
            selectedRow = null;
            btnDeletePeriod.disabled = true;
            tablaPeriodo.ajax.reload();
        }
    }
});


//Regresa a la principal
$("#home").on('click',function (){
    clearPanel();
});
$("#brand").on('click',function (){
    clearPanel();
});
function clearPanel(){
    $("#tablaEst").parents('div.dataTables_wrapper').first().hide();
    $("#tablaPeriod").parents('div.dataTables_wrapper').first().hide();
    $("#tablaAsig").parents('div.dataTables_wrapper').first().hide();
    if (!$("#tablaContainer").hasClass("hide")){
        $("#tablaContainer").addClass("hide");
    }
    if (!formEst.hasClass("hide")){
        formEst.addClass("hide");
    }
    if (!formPeriodo.hasClass("hide")){
        formPeriodo.addClass("hide");
    }
    if (!formAsig.hasClass("hide")){
        formAsig.addClass("hide");
    }
    if (!$("#tablaContainerPeriod").hasClass("hide")){
        $("#tablaContainerPeriod").addClass("hide");
    }
    if (!$("#tablaContainerAsig").hasClass("hide")){
        $("#tablaContainerAsig").addClass("hide")
    }
}

// Función para llenar la tabla con los datos de los estudiantes
function getStudents() {
    $.ajax({
        url: '/student/studentList',
        type: 'GET',
        success: function(data) {


            // Limpiar la tabla antes de agregar nuevos datos
            tableEst.clear();

            // Agregar cada estudiante a la tabla
            data.forEach(function(student) {
                tableEst.row.add([
                    student.matricula,
                    student.primerNom,
                    student.segNom,
                    student.primerApe,
                    student.segApe,
                    student.nacionalidad,
                    student.direccion,
                    student.catPago
                ]).draw();
            });


        },
        error: function(xhr, status, error) {
            console.error('Error al obtener los estudiantes: ' + error);
        }
    });
}



// Función para enviar los datos del formulario al controlador mediante AJAX
function guardarEstudiante() {
    // Obtener los valores del formulario
    var matricula = $("#matricula").val();
    var PrimerNom = $("#nombre1Estudiante").val();
    var SegNom = $("#nombre2Estudiante").val();
    var PrimerApe = $("#apellido1Estudiante").val();
    var SegApe = $("#apellido2Estudiante").val();
    var direccion = $("#direccion").val();
    var nacionalidad = $("#nacionalidad").val();
    var carrera = $("#carrera").val();
    var catPago = $("#catPago").val();
    // Objeto con los datos del estudiante
    var estudiante = {
        matricula: matricula,
        primerNom: PrimerNom,
        segNom: SegNom,
        primerApe: PrimerApe,
        segApe: SegApe,
        direccion: direccion,
        nacionalidad: nacionalidad,
        carrera: carrera,
        catPago: catPago
    };

    // Enviar los datos al controlador mediante AJAX
    $.ajax({
        url: "/student/saveStudent", // Ruta del controlador para guardar el estudiante
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(estudiante),
        success: function() {
            // Éxito: el estudiante se ha guardado correctamente
            alert("Estudiante guardado correctamente");
            formEst[0].reset();
            // Puedes realizar alguna otra acción después de guardar el estudiante, si es necesario.
        },
        error: function(xhr, status, error) {
            console.error("Error al guardar el estudiante: " + error);
        }
    });
}

function eliminarEstudiante(matricula){
    $.ajax({
        url: 'student/deleteStudent?matricula='+matricula,
        type: 'DELETE',
        success: function (){
            alert("Estudiante eliminado exitosamente")
            getStudents();
        } ,
        error: function (){
            alert("Error, no se pudo eliminar el estudiante.")
        }
    });
}

/**
 * Funciones para periodos
 */
function getPeriods(){
    $.ajax({
       url:'periodo/listaPeriodos',
       type:'GET',
       success: function (data){
           // Limpiar la tabla antes de agregar nuevos datos
           tablaPeriodo.clear();

           // Agregar cada estudiante a la tabla
           data.forEach(function(periodo) {
               tablaPeriodo.row.add([
                   periodo.codPeriodoAcad,
                   periodo.descripcion,
                   periodo.fechaInicio,
                   periodo.fechaFin,
                   periodo.fechaInicioClases,
                   periodo.fechaFinClases,
                   periodo.fechaLimitePago,
                   periodo.fechaLimitePrematricula,
                   periodo.fechaLimiteRetiro,
                   periodo.fechaLimitePublicacion
               ]).draw();
           });
       },
        error: function (error){
           console.log("Algo salio mal: "+error);
        }
    });
}

//Guardar periodo
function savePeriod(){
    // Obtener los valores del formulario
    var codPeriodo = $("#codPeriodAcad").val();
    var descripcion = $("#descripcion").val();
    var fechaIn = $("#fechaInicio").val();
    var fechaFin = $("#fechaFin").val();
    var fechaInClas= $("#fechaInicioClases").val();
    var fechaFinClas = $("#fechaFinClases").val();
    var fechaLimP = $("#fechaLimitePago").val();
    var fechaLimPre = $("#fechaLimitePrematricula").val();
    var fechaLimRe = $("#fechaLimiteRetiro").val();
    var fechaLimPu = $("#fechaLimitePublicacion").val();

    console.log(codPeriodo);
    // Objeto con los datos del estudiante
    var periodo = {
        codPeriodoAcad: codPeriodo,
        descripcion: descripcion,
        fechaInicio: fechaIn,
        fechaFin: fechaFin,
        fechaInicioClases: fechaInClas,
        fechaFinClases: fechaFinClas,
        fechaLimitePago: fechaLimP,
        fechaLimitePrematricula: fechaLimPre,
        fechaLimiteRetiro: fechaLimRe,
        fechaLimitePublicacion: fechaLimPu
    };

    // Enviar los datos al controlador mediante AJAX
    $.ajax({
        url: "/periodo/savePeriodo", // Ruta del controlador para guardar el estudiante
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(periodo),
        success: function() {
            // Éxito: el estudiante se ha guardado correctamente
            alert("Periodo guardado correctamente");
            formPeriodo[0].reset();
        },
        error: function(xhr, status, error) {
            console.error("Error al guardar el Periodo: " + error);
        }
    });
}

//Funcion eliminar periodo
function eliminarPeriodo(codPeriod){
    $.ajax({
        url: 'periodo/deletePeriod?codPeriodoAcad='+codPeriod,
        type: 'DELETE',
        success: function (){
            alert("Periodo eliminado exitosamente")
            getPeriods();
        } ,
        error: function (){
            alert("Error, no se pudo eliminar el periodo.")
        }
    });
}

/**
 * Asignatura funciones
 */

function getAsig(){
    $.ajax({
        url:'asignatura/listAsig',
        type:'GET',
        success: function (data){
            // Limpiar la tabla antes de agregar nuevos datos
            tablaAsig.clear();

            // Agregar cada estudiante a la tabla
            data.forEach(function(asignatura) {
                tablaAsig.row.add([
                    asignatura.codAsignatura,
                    asignatura.nombre,
                    asignatura.creditos,
                    asignatura.horasTeoricas,
                    asignatura.horasPracticas
                ]).draw();
            });
        },
        error: function (error){
            console.log("Algo salio mal: "+error);
        }
    });
}

//Guardar asignatura
function saveAsig(){
    // Obtener los valores del formulario
    var codAsig = $("#codAsig").val();
    var nombre = $("#nombreAsig").val();
    var creditos = $("#creditos").val();
    var horasTeo = $("#horasTeoricas").val();
    var horasPrac= $("#horasPracticas").val();

    // Objeto con los datos de asignatura
    var asignatura = {
        codAsignatura: codAsig,
        nombre: nombre,
        creditos: creditos,
        horasTeoricas: horasTeo,
        horasPracticas: horasPrac
    };

    // Enviar los datos al controlador mediante AJAX
    $.ajax({
        url: "asignatura/saveAsig", // Ruta del controlador para guardar el estudiante
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(asignatura),
        success: function() {
            // Éxito: el estudiante se ha guardado correctamente
            alert("Asignatura guardada correctamente");
            formAsig[0].reset();
        },
        error: function(xhr, status, error) {
            console.error("Error al guardar la Asignatura: " + error);
        }
    });
}

//Eliminar asignatura
function deleteAsig(codAsig){
    $.ajax({
        url: 'asignatura/deleteAsig?codAsignatura='+codAsig,
        type: 'DELETE',
        success: function (){
            alert("Asignatura eliminada exitosamente")
            getAsig();
        } ,
        error: function (){
            alert("Error, no se pudo eliminar la Asignatura.")
        }
    });
}