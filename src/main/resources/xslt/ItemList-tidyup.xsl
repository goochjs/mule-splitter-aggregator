<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" encoding="utf-8" omit-xml-declaration="no" indent="yes"/>
	
	<xsl:param name="schemaLocation" select="'schemaLocation'"/>
	
	<xsl:template match="node()|@*" name="copy">
		<xsl:copy>
			<xsl:apply-templates select="node()|@*"/>
		</xsl:copy>
	</xsl:template>
	
	<xsl:template match="@*">
	
		<xsl:choose>
			<xsl:when test="local-name() = $schemaLocation">
				<!-- do nothing (i.e. it will be suppressed) -->
			</xsl:when>
			
			<!-- simply copy everything else -->
			<xsl:otherwise>
				<xsl:call-template name="copy"/>
			</xsl:otherwise>
		</xsl:choose>
		
	</xsl:template>
</xsl:stylesheet>
