<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:p="http://www.coffeeShop.com.vn"   version="1.0">
    <xsl:output method="xml" encoding="UTF-8"/>
    <xsl:param name="pathFile" select="src"/>
    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/">
        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="x" page-height="8.5in"
                                       page-width="11in" margin-top="0.5in" margin-bottom="0.5in"
                                       margin-left="1in" margin-right="1in">
                    <fo:region-body margin-top="1in" margin-bottom="1in"/>
                    <fo:region-before extent="0.5in"/>
                    <fo:region-after extent="0.5in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            <fo:page-sequence master-reference="x">
                <fo:static-content flow-name="xsl-region-before">
                    <fo:block font-size="14pt" font-family="Arial"
                              space-after.optimum="15pt" text-align="center">
                        Menu
                    </fo:block>
                </fo:static-content>
                <fo:static-content flow-name="xsl-region-after">
                    <fo:block font-size="18pt" font-family="Arial"
                              line-height="24pt" space-after.optimum="15pt"
                              text-align="center" padding-top="3pt" >
                    </fo:block>
                </fo:static-content>
                <fo:flow flow-name="xsl-region-body">
                    <xsl:for-each select="p:menu/p:category"> 
                        <!--                        <xsl:variable name="index" select="position() -1">-->
                        <fo:block>
                            <xsl:variable name="index" select="(position() - 1)"/>
                            <xsl:value-of select="$index" />
                        </fo:block>
                          
                        <!--                        <xsl:if test="((position() % 3)= = 0">
                            <fo:block>
                              ended<xsl:value-of select="position()"/>
                            </fo:block>
                        </xsl:if>-->
                        <!--                        <xsl:if test="(position() - 1) % 3 = 0">
                            <fo:block>
                               start<xsl:value-of select="position()"/>
                            </fo:block>
                        </xsl:if>-->
                       
                    </xsl:for-each>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>

</xsl:stylesheet>
