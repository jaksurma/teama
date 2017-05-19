var serverAddress = "http://localhost:8080";
var map;
var mercator = new OpenLayers.Projection("EPSG:900913");
var geographic = new OpenLayers.Projection("EPSG:4326");
var markers;
var refresh = 3000;

function init() {
    var options = {
        projection: mercator,
        displayProjection: geographic,
        units: "m",
        maxResolution: 156543.0339,
        maxExtent: new OpenLayers.Bounds(-20037508.34, -20037508.34, 20037508.34, 20037508.34),
        numZoomLevels: 1
    };
    map = new OpenLayers.Map('map', options);

    var osm = new OpenLayers.Layer.OSM();

    var gmap = new OpenLayers.Layer.Google("Google", { sphericalMercator: true });

    markers = new OpenLayers.Layer.Markers("points");

    map.addLayers([osm, gmap]);

    map.addLayer(markers);

    map.addControl(new OpenLayers.Control.LayerSwitcher());
    map.addControl(new OpenLayers.Control.MousePosition());

    map.setCenter(new OpenLayers.LonLat(18.4718, 54.37719).transform(geographic, mercator), 15);

    var click = new OpenLayers.Control.Click();
    map.addControl(click);
    click.activate();

    var events = map.events;
    events.register("mousedown", map, function (e) {
        destroyPopups();
        return true;
    }, true);
}

OpenLayers.Control.Click = OpenLayers.Class(OpenLayers.Control, {
    defaultHandlerOptions: {
        'single': true,
        'double': false,
        'pixelTolerance': 0,
        'stopSingle': false,
        'stopDouble': false
    },
    initialize: function (options) {
        this.handlerOptions = OpenLayers.Util.extend({}, this.defaultHandlerOptions);
        OpenLayers.Control.prototype.initialize.apply(this, arguments);
    }
});

function createPopup(feature) {
    if (!feature.popup) {
        feature.popup = feature.createPopup(feature.closeBox);
    }
    map.addPopup(feature.popup);
    feature.popup.show();
}

function destroyPopups() {
    for (var i = 0; i < markers.markers.length; i++) {
        if (markers.markers[i].feature.popup) {
            markers.markers[i].feature.popup.destroy();
            markers.markers[i].feature.popup = null;
        }
    }
}

function getMarkers(selectable) {
    $.ajax({
        url: serverAddress + "/gates",
        cache: false,
        dataType: "json",
        success: function (response) {
            if (response) {
                addMarkers(response.gates, selectable);
            }
        }
    });
}

function addMarkers(gates, selectable) {
    for (var i = 0; i < gates.length; i++) {
        addMarker(new OpenLayers.LonLat(gates[i].lon, gates[i].lat).transform(geographic, mercator), gates[i].number, selectable);
    }
}

function addMarker(lonlat, gateNumber, selectable) {
    var feature = new OpenLayers.Feature(markers, lonlat);
    feature.closeBox = true;
    feature.popupClass = OpenLayers.Popup.FramedCloud;
    feature.data.overflow = "auto";

    var marker = feature.createMarker();
    marker.number = gateNumber;
    marker.feature = feature;
    if (selectable) {
        feature.data.popupContentHTML = '<input type="button" value="Wybieram ' + marker.number + '" onclick="selectMarker(' + marker.number + ')">';
    } else {
        feature.data.popupContentHTML = null;
    }

    if (selectable) {
        var markerClick = function (evt) {
            destroyPopups();
            if (!this.popup) {
                this.popup = this.createPopup(this.closeBox);
                map.addPopup(this.popup);
                this.popup.show();
            }
            currentPopup = this.popup;
            OpenLayers.Event.stop(evt);
        };
        marker.events.register("mousedown", feature, markerClick);
    }
    markers.addMarker(marker);
}

function selectMarker(num) {
    $.ajax({
        url: serverAddress + "/gates",
        cache: false,
        type: "POST",
        data: "{ gate: " + num + " }",
        dataType: "json",
        success: function (response) {
            if (response) {
                destroyPopups();
            }
        },
        error: function (err) {
            if (err && err.responseJSON) {
                var response = err.responseJSON;
            }
        }
    });
}

function addSelectedMarker(selectedGate) {
    destroyPopups();
    for (var i = 0; i < markers.markers.length; i++) {
        if (markers.markers[i].number === selectedGate) {
            markers.markers[i].feature.data.popupContentHTML = 'Gate ' + markers.markers[i].number + ' wybrany.';
            createPopup(markers.markers[i].feature);
        } else {
            markers.markers[i].feature.data.popupContentHTML = null;
        }
    }
}

function update() {
    $.ajax({
        url: serverAddress + "/gates/selected",
        cache: false,
        dataType: "json",
        success: function (response) {
            addSelectedMarker(response.gate);
            setTimeout(update, refresh);
        },
        error: function () {
            setTimeout(update, refresh);
        }
    });
}
