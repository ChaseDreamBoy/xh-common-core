package com.xh.common.util;

import org.locationtech.jts.geom.*;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

import java.util.List;

/**
 * GeoUtil
 *
 * @author xh
 * @date 2021/12/10
 */
public class GeoUtil {

    public static GeoUtil INSTANCE = new GeoUtil();

    private final GeometryFactory geometryFactory;

    private final WKTReader reader;

    public GeoUtil() {
        PrecisionModel precisionModel = new PrecisionModel(PrecisionModel.FLOATING_SINGLE);
        this.geometryFactory = new GeometryFactory(precisionModel);
        this.reader = new WKTReader(this.geometryFactory);
    }

    public Geometry getGeo(String geoWkt) throws ParseException {
        return reader.read(geoWkt);
    }

    /**
     * unionAll
     *
     * @param geoWktList geoWtk list
     * @return Geometry
     * @throws ParseException ParseException
     */
    public Geometry unionAll(List<String> geoWktList) throws ParseException {
        Geometry geoAll = null;
        for (String geoWkt1 : geoWktList) {
            Geometry geo1 = this.reader.read(geoWkt1);
            if (geoAll == null) {
                geoAll = geo1;
            } else {
                geoAll = geoAll.union(geo1);
            }
        }
        return geoAll;
    }

    /**
     * geoWktList 是否包含 geoWkt2
     *
     * @param geoWktList geoWktList
     * @param geoWkt2    geoWkt2
     * @param distance   向外缓冲，单位:度,distance传0.01，表示约1公里
     * @return true 表示包含，false表示不包含
     * @throws ParseException ParseException
     */
    public boolean isContain(List<String> geoWktList, String geoWkt2, double distance) throws ParseException {
        Geometry geoAll = this.unionAll(geoWktList);
        if (distance > 0.0D) {
            // 扩大区域
            geoAll = geoAll.buffer(distance);
        }
        Geometry geo2 = this.reader.read(geoWkt2);
        return geoAll.contains(geo2);
    }

    /**
     * 获取相交区域
     *
     * @param geoWktList geoWktList
     * @param geoWkt2    geoWktList
     * @return 相交区域
     * @throws ParseException ParseException
     */
    public String getIntersect(List<String> geoWktList, String geoWkt2) throws ParseException {
        Geometry geoAll = this.unionAll(geoWktList);
        Geometry geo2 = this.reader.read(geoWkt2);
        Geometry intersectGeo = geoAll.intersection(geo2);
        return intersectGeo.toText();
    }

}
