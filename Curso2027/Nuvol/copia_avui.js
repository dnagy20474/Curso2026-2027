// ================== CONFIGURACIÓN ==================
const HOJA_AVUI = 'AVUI';
const DIAS = {            // getDay(): 1 = lunes ... 5 = viernes
    1: 'DILLUNS',
    2: 'DIMARTS',
    3: 'DIMECRES',
    4: 'DIJOUS',
    5: 'DIVENDRES'
};
const ID_CARPETA_DRIVE = ''; // Opcional: ID de la carpeta de Drive. Vacío = raíz de "Mi unidad"
const PREFIJO_PDF = 'Guardies_';

// ================== 1) COPIAR EL DÍA A "AVUI" (6:00) ==================
function copiarDiaAAvui() {
    const ss = SpreadsheetApp.getActiveSpreadsheet();
    const diaSemana = new Date().getDay();
    const nombreHoja = DIAS[diaSemana];

    if (!nombreHoja) {
        Logger.log('Fin de semana: no se copia nada.');
        return;
    }

    const origen = ss.getSheetByName(nombreHoja);
    const destino = ss.getSheetByName(HOJA_AVUI);

    if (!origen || !destino) {
        throw new Error('No se encuentra la hoja "' + nombreHoja + '" o "' + HOJA_AVUI + '"');
    }

    // Limpiar destino y copiar contenido + formato + validaciones
    destino.clear();
    const rangoOrigen = origen.getDataRange();
    rangoOrigen.copyTo(destino.getRange(1, 1), SpreadsheetApp.CopyPasteType.PASTE_NORMAL, false);

    // Copiar también anchos de columna
    for (let c = 1; c <= rangoOrigen.getNumColumns(); c++) {
        destino.setColumnWidth(c, origen.getColumnWidth(c));
    }

    SpreadsheetApp.flush();
    Logger.log('Copiado ' + nombreHoja + ' -> ' + HOJA_AVUI);
}

// ================== 2) GENERAR PDF Y GUARDARLO EN DRIVE (12:00) ==================
function generarPdfAvui() {
    const diaSemana = new Date().getDay();
    if (!DIAS[diaSemana]) {
        Logger.log('Fin de semana: no se genera PDF.');
        return;
    }

    const ss = SpreadsheetApp.getActiveSpreadsheet();
    const hoja = ss.getSheetByName(HOJA_AVUI);
    SpreadsheetApp.flush();

    const url = 'https://docs.google.com/spreadsheets/d/' + ss.getId() + '/export?' +
        'format=pdf' +
        '&gid=' + hoja.getSheetId() +   // solo la hoja AVUI
        '&size=A4' +
        '&portrait=false' +             // horizontal
        '&fitw=true' +                  // ajustar al ancho
        '&gridlines=false' +
        '&sheetnames=false&printtitle=false&pagenumbers=false';

    const respuesta = UrlFetchApp.fetch(url, {
        headers: { Authorization: 'Bearer ' + ScriptApp.getOAuthToken() },
        muteHttpExceptions: true
    });

    if (respuesta.getResponseCode() !== 200) {
        throw new Error('Error al exportar el PDF: ' + respuesta.getContentText());
    }

    const fecha = Utilities.formatDate(new Date(), Session.getScriptTimeZone(), 'yyyy-MM-dd');
    const blob = respuesta.getBlob().setName(PREFIJO_PDF + fecha + '.pdf');

    const carpeta = ID_CARPETA_DRIVE
        ? DriveApp.getFolderById(ID_CARPETA_DRIVE)
        : DriveApp.getRootFolder();

    carpeta.createFile(blob);
    Logger.log('PDF guardado: ' + blob.getName());
}

// ================== 3) CREAR LOS ACTIVADORES (ejecutar UNA sola vez) ==================
function crearActivadores() {
    // Borrar activadores anteriores para no duplicar
    ScriptApp.getProjectTriggers().forEach(t => ScriptApp.deleteTrigger(t));

    // Todos los días a las 6:00 -> copia el día a AVUI
    ScriptApp.newTrigger('copiarDiaAAvui')
        .timeBased()
        .everyDays(1)
        .atHour(6)
        .create();

    // Todos los días a las 12:00 -> genera el PDF
    ScriptApp.newTrigger('generarPdfAvui')
        .timeBased()
        .everyDays(1)
        .atHour(12)
        .create();
}