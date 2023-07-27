/**
 * Grupo y horarios
 */
//Variables
var formGrup = $("#formGrup"); //Formulario de grupo
var formHorario = $("#formHorario"); // parte del formulario con horario
var btnSaveGrup = document.querySelector("#saveGrup"); //boton para guardar grupos
var btnContinue = document.querySelector("#continue"); //boton para abrir el formulario de grupos

var tablaGrup = $("#tablaGrupo").DataTable({
    select: true
});//tabla de grupos
var tablaHorario = $("#tablaHorario").DataTable({
    select: true
});
$("#tablaGrupo").parents('div.dataTables_wrapper').first().hide();

var deleteGrup = document.querySelector("#deleteGrupo"); //borrar grupo
var horario = document.querySelector("#modalGrup"); //Ver horario
var btnEditHorario = document.querySelector("#btnEditarHorario");
var btnDeleteHorario = document.querySelector("#btnEliminarHorario");
var btnAddHorario = document.querySelector("#btnAddHorario");
var btnEdiGrup = document.querySelector("#btneditGrupo");
var formSaveHorario = $("#formSaveHorario");
//ver form grup
$("#insertGrup").on('click',function (){
   clearPanel();
   formGrup.removeClass("hide");
});

$("#listaGrup").on('click',function (){
   clearPanel();
   getGrupos();
    $("#tablaGrupo").parents('div.dataTables_wrapper').first().show();
    $("#tablaContainerGrupo").removeClass("hide");
});

//Cuando se presiona a siguiente:
$("#continue").on('click',function (){
    if ($("#continue").text() !='Cancelar'){
        var confirmar = window.confirm("Desea crear un horario a este grupo?");
        if (confirmar){
            formHorario.removeClass("hide");
            $("#continue").text("Cancelar");
        }
        else {
            saveGrup();

        }
    }
    else {
        //volver a ocultar el formulario
        formHorario.addClass("hide");
        $("#continue").text("Siguiente");
    }

});

//Guardar formulario
$("#saveGrup").on('click',function () {
    console.log("guardand");
    saveGrup();
});


//Seleccionar en la tabla grupos

$("#tablaGrupo tbody").on('click','tr',function (){


    if($(this).hasClass('selected')){
        $(this).removeClass('selected');
        selectedRow = null;
        deleteGrup.disabled = true;
        horario.disabled = true;
        btnAddHorario.disabled = true;
        btnEdiGrup.disabled = true;
    }
    else{
        tablaGrup.$('tr.selected').removeClass('selected');
        $(this).addClass('selected');
        deleteGrup.disabled= false;
        horario.disabled = false;
        btnAddHorario.disabled = false;
        btnEdiGrup.disabled = false;
        selectedRow = tablaGrup.row(this).data();

        console.log(selectedRow)
    }

});
//Eliminar grupo
$("#deleteGrupo").on('click',function (){
    if (selectedRow !==null){
        var codPeriod = selectedRow[0];
        var codAsig = selectedRow[1];
        var numGrup = selectedRow[2];
        // Mostrar ventana de confirmación
        var confirmar = window.confirm('¿Estás seguro de que deseas eliminar a esta Asignatura?');

        // Si el usuario hace clic en "Aceptar", proceder con la eliminación
        if (confirmar) {
            eliminarGrup(codPeriod,codAsig,numGrup);
            selectedRow = null;
            deleteGrup.disabled = true;
            horario.disabled = true;
            tablaGrup.ajax.reload();
        }
    }
});

//Ver horario de grupos
$("#modalGrup").on('click',function (){

    if (selectedRow !==null){
        var codPeriod = selectedRow[0];
        var codAsig = selectedRow[1];
        var numGrup = selectedRow[2];

        // Si el usuario hace clic en "Aceptar", proceder con la eliminación
        getHorario(selectedRow)

        selectedRow = null;
        deleteGrup.disabled = true;
        horario.disabled = true;
        clearPanel();
        $("#tablaContainerHorario").removeClass("hide")


    }
});

//cerrar horario
$("#btnCerrarHorario").on('click', function (){
   clearPanel();

   $("#tablaContainerGrupo").removeClass("hide");
    $("#tablaGrupo").parents('div.dataTables_wrapper').first().show();
});

//SELECCIONAR tabla horario
$("#tablaHorario tbody").on('click','tr',function (){


    if($(this).hasClass('selected')){
        $(this).removeClass('selected');
        selectedRow = null;
        btnEditHorario.disabled = true;
        btnDeleteHorario.disabled = true;
    }
    else{
        tablaHorario.$('tr.selected').removeClass('selected');
        $(this).addClass('selected');
        btnEditHorario.disabled= false;
        btnDeleteHorario.disabled = false;
        selectedRow = tablaHorario.row(this).data();

    }

});

//Eliminar horario
$("#btnEliminarHorario").on('click',function (){
    if (selectedRow !==null){
        var codPeriod = selectedRow[0];
        var codAsig = selectedRow[1];
        var numGrup = selectedRow[2];
        // Mostrar ventana de confirmación
        var confirmar = window.confirm('¿Estás seguro de que deseas eliminar a esta Asignatura?');

        // Si el usuario hace clic en "Aceptar", proceder con la eliminación
        if (confirmar) {
            eliminarHorario(codPeriod,codAsig,numGrup);
            selectedRow = null;
            btnDeleteHorario.disabled = true;
            btnEditHorario.disabled = true;
            tablaHorario.ajax.reload();
        }
    }
});
//Editar Horario
$("#btnEditarHorario").on('click',function (){
    $("#codPeriodH").val(selectedRow[0]);
    $("#codAsigH").val(selectedRow[1]);
    $("#numGrupH").val(selectedRow[2]);
    clearPanel();
    formSaveHorario.removeClass("hide");
    $("#saveHorario").text("Actualizar");
});
//Editar grupo
$("#btneditGrupo").on('click',function (){
    $("#codPeriodGrup").val(selectedRow[0]);
    $("#codAsigGrup").val(selectedRow[1]);
    $("#numGrup").val(selectedRow[2]);
    clearPanel();
    formGrup.removeClass("hide");
    $("#continue").addClass("hide");
    $("#codPeriodGrup").prop('disabled',true);
    $("#codAsigGrup").prop('disabled',true);
    $("#numGrup").prop('disabled',true);
    $("#saveChangesGrup").removeClass("hide");

});

//Guardar edicion
$("#saveChangesGrup").on('click',function (){
   updateGrupo();
});

//Añadir horario
$("#btnAddHorario").on('click',function (){
    $("#codPeriodH").val(selectedRow[0]);
    $("#codAsigH").val(selectedRow[1]);
    $("#numGrupH").val(selectedRow[2]);
    clearPanel();
    formSaveHorario.removeClass("hide");
});

//guardar horario
$("#saveHorario").on('click',function (){
    if ($("#saveHorario").text() == 'Actualizar'){
        updateHorario();
    }
    else{
        saveHorarioOnly();
    }
});

//cancelar guardad
$("#cancelHorario").on('click',function (){
   clearPanel();
    $("#tablaContainerGrupo").removeClass("hide");
    $("#tablaGrupo").parents('div.dataTables_wrapper').first().show();
});
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
    $("#tablaGrupo").parents('div.dataTables_wrapper').first().hide();
    btnAddHorario.disabled = true;
    btnEdiGrup.disabled = true;
    btnEditHorario.disabled = true;
    btnDeleteHorario.disabled = true;
    btnDeleteAsig.disabled = true;
    btnDeletePeriod.disabled = true;
    botonEliminarEst.disabled = true;
    deleteGrup.disabled = true;
    $("#modalGrup").prop('disabled',true);
    $("#saveHorario").text("Guardar Horario")
    selectedRow = null;
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
    if (!formGrup.hasClass("hide")){
        formGrup.addClass("hide");
        if (!formHorario.hasClass("hide")){
            formHorario.addClass("hide");
            $("#continue").text("Siguiente");
        }

    }
    if (!formSaveHorario.hasClass("hide")){
        formSaveHorario.addClass("hide");
    }
    if (!$("#tablaContainerPeriod").hasClass("hide")){
        $("#tablaContainerPeriod").addClass("hide");
    }
    if (!$("#tablaContainerAsig").hasClass("hide")){
        $("#tablaContainerAsig").addClass("hide")
    }
    if (!$("#tablaContainerGrupo").hasClass("hide")){
        $("#tablaContainerGrupo").addClass("hide");
    }
    if (!$("#tablaContainerHorario").hasClass("hide")){
        $("#tablaContainerHorario").addClass("hide");
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


/**
 * Funciones para grupos
 */

//Obtener Grupos
function getGrupos(){
    $.ajax({
        url:'group/groupList',
        type:'GET',
        success: function (data){
            // Limpiar la tabla antes de agregar nuevos datos
            tablaGrup.clear();
            // Agregar cada estudiante a la tabla
            data.forEach(function(grupo) {
                tablaGrup.row.add([
                    grupo.codPeriodoAcad,
                    grupo.codAsignatura,
                    grupo.numGrupo,
                    grupo.cupoGrupo,
                    grupo.horario
                ]).draw();


            });
        },
        error: function (error){
            console.log("Algo salio mal: "+error);
        }
    });
}

//Obtener horario de grupo dado el mismo

function getHorario(codPeriod,codAsig,numGrup){
    // Obtener los valores del formulario
    var codPeriodGrup = codPeriod;
    var codAsig = codAsig;
    var numGrup = numGrup;

    $.ajax({
        url:'group/groupSchedule?codPeriodoAcad='+codPeriodGrup+'&codAsignatura='+codAsig+'&numGrupo='+numGrup,
        type:'GET',
        success: function (data){
            // Limpiar la tabla antes de agregar nuevos datos
            tablaHorario.clear();
            // Agregar cada estudiante a la tabla
            console.log(data);
            data.forEach(function(horario) {
                    console.log(horario.dia);
                    tablaHorario.row.add([
                        codPeriodGrup,
                        codAsig,
                        numGrup,
                        horario.dia,
                        horario.fechaInicio,
                        horario.fechaFin,
                        horario.horaInicio,
                        horario.horaFin
                    ]).draw();



            });
        },
        error: function (error){
            console.log("Algo salio mal: "+error);
        }
    });
}

//Guardar grupo
function saveGrup(){
    // Obtener los valores del formulario
    var codPeriodGrup = $("#codPeriodGrup").val();
    var codAsig = $("#codAsigGrup").val();
    var numGrup = $("#numGrup").val();
    var cupo = $("#cupo").val();
    var horario= $("#horario").val();

    // Objeto con los datos del grupo
    var grupo = {
        codPeriodoAcad: codPeriodGrup,
        codAsignatura: codAsig,
        numGrupo: numGrup,
        cupoGrupo: cupo,
        horario: horario
    };

    // Enviar los datos al controlador mediante AJAX
    $.ajax({
        url: "group/saveGroup", // Ruta del controlador para guardar el estudiante
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(grupo),
        success: function() {
            // Éxito: el grupo se ha guardado correctamente
            alert("Grupo guardado correctamente");
            if (!$("#formHorario").hasClass("hide")){
                saveHorario();
                console.log("Guardando horario")
            }
            else {
                formGrup[0].reset();
            }
        },
        error: function(xhr, status, error) {
            console.error("Error al guardar la Asignatura: " + error);
        }
    });
}

function saveHorario(){
    // Obtener los valores del formulario
    var codPeriodGrup = $("#codPeriodGrup").val();
    var codAsig = $("#codAsigGrup").val();
    var numGrup = $("#numGrup").val();
    var dia = $("#dia").val();
    var fechaInicio= $("#fechaInGrup").val();
    var fechaFin= $("#fechaFinGrup").val();
    var horaInicio= $("#horaInicio").val();
    var horaFin= $("#horaFin").val();

    // Objeto con los datos del grupo
    var grupo = {
        codPeriodoAcad: codPeriodGrup,
        codAsignatura: codAsig,
        numGrupo: numGrup,
        dia: dia,
        fechaInicio: fechaInicio,
        fechaFin: fechaFin,
        horaInicio: horaInicio,
        horaFin: horaFin
    };
    console.log("Guardando Horario");
    // Enviar los datos al controlador mediante AJAX
    $.ajax({
        url: "group/saveSchedule", // Ruta del controlador para guardar el estudiante
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(grupo),
        success: function() {
            // Éxito: el estudiante se ha guardado correctamente
            alert("Horario guardado correctamente");
            formGrup[0].reset();
            getHorario(codPeriodGrup,codAsig,numGrup)

            formHorario.addClass("hide");
            $("#continue").text("Siguiente");

        },
        error: function(xhr, status, error) {
            console.error("Error al guardar el horario: " + error);
        }
    });
}

//Eliminar grupo
function eliminarGrup(codPeriodo,codAsig,numGrupo){
    $.ajax({
        url: 'group/deleteGroup?NumGrupo='+numGrupo+'&CodPeriodoAcad='+codPeriodo+'&CodAsignatura='+codAsig,
        type: 'DELETE',
        success: function (){
            alert("Grupo eliminado exitosamente")
            getGrupos();
        } ,
        error: function (){
            alert("Error, no se pudo eliminar el grupo.")
        }
    });
}

//ELIMINAR HORARIO
function eliminarHorario(codPeriod,codAsig,numGrupo){
    $.ajax({
        url: 'group/deleteSchedule?NumGrupo='+numGrupo+'&CodPeriodoAcad='+codPeriod+'&CodAsignatura='+codAsig,
        type: 'DELETE',
        success: function (){
            alert("Horario eliminado exitosamente");
            location.reload();

        } ,
        error: function (){
            alert("Error, no se pudo eliminar el Horario.")
        }
    });
}

function updateGrupo(){
    // Obtener los valores del formulario
    var codPeriodGrup = $("#codPeriodGrup").val();
    var codAsig = $("#codAsigGrup").val();
    var numGrup = $("#numGrup").val();
    var cupo = $("#cupo").val();
    var horario= $("#horario").val();

    // Objeto con los datos del grupo
    var grupo = {
        codPeriodoAcad: codPeriodGrup,
        codAsignatura: codAsig,
        numGrupo: numGrup,
        cupoGrupo: cupo,
        horario: horario
    };

    // Enviar los datos al controlador mediante AJAX
    $.ajax({
        url: "group/updateGroup", // Ruta del controlador para guardar el estudiante
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(grupo),
        success: function() {
            // Éxito: el grupo se ha guardado correctamente
            alert("Grupo guardado correctamente");

            formGrup[0].reset();
            $("#continue").removeClass("hide");
            $("#codPeriodGrup").prop('disabled',false);
            $("#codAsigGrup").prop('disabled',false);
            $("#numGrup").prop('disabled',false);

            $("#saveChangesGrup").addClass("hide");
            getHorario(codPeriodGrup,codAsig,numGrup)

            clearPanel();
            getGrupos();
            $("#tablaGrupo").parents('div.dataTables_wrapper').first().show();
            $("#tablaContainerGrupo").removeClass("hide");

        },
        error: function(xhr, status, error) {
            console.error("Error al guardar el Grupo: " + error);
        }
    });
}

function saveHorarioOnly(){
    // Obtener los valores del formulario
    var codPeriodGrup = $("#codPeriodH").val();
    var codAsig = $("#codAsigH").val();
    var numGrup = $("#numGrupH").val();
    var dia = $("#diaH").val();
    var fechaInicio= $("#fechaInGrupH").val();
    var fechaFin= $("#fechaFinGrupH").val();
    var horaInicio= $("#horaInicioH").val();
    var horaFin= $("#horaFinH").val();

    // Objeto con los datos del grupo
    var grupo = {
        codPeriodoAcad: codPeriodGrup,
        codAsignatura: codAsig,
        numGrupo: numGrup,
        dia: dia,
        fechaInicio: fechaInicio,
        fechaFin: fechaFin,
        horaInicio: horaInicio,
        horaFin: horaFin
    };
    console.log("Guardando Horario");
    // Enviar los datos al controlador mediante AJAX
    $.ajax({
        url: "group/saveSchedule", // Ruta del controlador para guardar el estudiante
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(grupo),
        success: function() {
            // Éxito: el estudiante se ha guardado correctamente
            alert("Horario añadido correctamente");
            formSaveHorario[0].reset();

            formSaveHorario.addClass("hide");
            clearPanel();
            getHorario(codPeriodGrup,codAsig,numGrup)

            $("#tablaContainerGrupo").removeClass("hide");
            $("#tablaGrupo").parents('div.dataTables_wrapper').first().show();
        },
        error: function(xhr, status, error) {
            console.error("Error al añadir el horario: " + error);
        }
    });
}

function updateHorario(){
    // Obtener los valores del formulario
    var codPeriodGrup = $("#codPeriodH").val();
    var codAsig = $("#codAsigH").val();
    var numGrup = $("#numGrupH").val();
    var dia = $("#diaH").val();
    var fechaInicio= $("#fechaInGrupH").val();
    var fechaFin= $("#fechaFinGrupH").val();
    var horaInicio= $("#horaInicioH").val();
    var horaFin= $("#horaFinH").val();

    // Objeto con los datos del grupo
    var grupo = {
        codPeriodoAcad: codPeriodGrup,
        codAsignatura: codAsig,
        numGrupo: numGrup,
        dia: dia,
        fechaInicio: fechaInicio,
        fechaFin: fechaFin,
        horaInicio: horaInicio,
        horaFin: horaFin
    };
    console.log(codPeriodGrup,codAsig,numGrup);
    // Enviar los datos al controlador mediante AJAX
    $.ajax({
        url: "group/updateSchedule", // Ruta del controlador para guardar el estudiante
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(grupo),
        success: function() {
            // Éxito: el grupo se ha guardado correctamente
            alert("Grupo guardado correctamente");

            formSaveHorario[0].reset();
            getHorario(codPeriodGrup,codAsig,numGrup)
            clearPanel();

            $("#tablaGrupo").parents('div.dataTables_wrapper').first().show();
            $("#tablaContainerGrupo").removeClass("hide");

        },
        error: function(xhr, status, error) {
            console.error("Error al guardar el Grupo: " + error);
        }
    });
}