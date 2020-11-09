<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:exsl="http://exslt.org/common" extension-element-prefixes="exsl"
	xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:svg="http://www.w3.org/2000/svg" version="1.0" xmlns:rx="http://www.renderx.com/XSL/Extensions">

	<xsl:template match="/">
		<xsl:processing-instruction name="xep-pdf-bleed">0.25in</xsl:processing-instruction>
		<xsl:processing-instruction name="xep-pdf-bleed-mark-width">0pt</xsl:processing-instruction>
		<xsl:processing-instruction name="xep-pdf-crop-offset">0.25in</xsl:processing-instruction>
		<xsl:processing-instruction name="xep-pdf-crop-mark-width">0pt</xsl:processing-instruction>


<!-- 4C
		<xsl:processing-instruction name="xep-pdf-bleed">0.25in</xsl:processing-instruction>
		<xsl:processing-instruction name="xep-pdf-bleed-mark-width">1pt</xsl:processing-instruction>
		<xsl:processing-instruction name="xep-pdf-crop-offset">-0.25in</xsl:processing-instruction>
		<xsl:processing-instruction name="xep-pdf-crop-mark-width">5pt</xsl:processing-instruction>
-->

<!-- 4B
		<xsl:processing-instruction name="xep-pdf-bleed">0.25in</xsl:processing-instruction>
		<xsl:processing-instruction name="xep-pdf-bleed-mark-width">1pt</xsl:processing-instruction>
		<xsl:processing-instruction name="xep-pdf-crop-offset">0.25in</xsl:processing-instruction>
		<xsl:processing-instruction name="xep-pdf-crop-mark-width">5pt</xsl:processing-instruction>
-->


<!-- 4A
		<xsl:processing-instruction name="xep-pdf-bleed">0.25in</xsl:processing-instruction>
		<xsl:processing-instruction name="xep-pdf-bleed-mark-width">1pt</xsl:processing-instruction>
		<xsl:processing-instruction name="xep-pdf-crop-offset">0.25in</xsl:processing-instruction>
		<xsl:processing-instruction name="xep-pdf-crop-mark-width">5pt</xsl:processing-instruction>
-->

<!--
		<xsl:processing-instruction name="xep-pdf-crop-offset">-0.25in</xsl:processing-instruction>
		<xsl:processing-instruction name="xep-pdf-crop-mark-width">5pt</xsl:processing-instruction>
		<xsl:processing-instruction name="xep-pdf-bleed">0.25in</xsl:processing-instruction>
		<xsl:processing-instruction name="xep-pdf-bleed-mark-width">1pt</xsl:processing-instruction>
-->
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
			<fo:layout-master-set>
				<fo:simple-page-master master-name="front-page" page-width="9.5in" page-height="6.5in">
					<fo:region-body margin="0in"/>
				</fo:simple-page-master>
				
				<fo:simple-page-master master-name="back-page"  page-width="9.5in" page-height="6.5in">
					<fo:region-body margin="0in"/>
          
				</fo:simple-page-master>
			</fo:layout-master-set>

			<xsl:for-each select="//record">
				<xsl:if test="not(fullname='FULLNAME')">
					<fo:page-sequence master-reference="front-page" force-page-count="no-force">
						<fo:flow flow-name="xsl-region-body">

				      <fo:block margin-top="0in" margin-left="0in">
								<xsl:call-template name="front"/>
					    </fo:block>

						</fo:flow>
					</fo:page-sequence>

					<fo:page-sequence master-reference="back-page" force-page-count="no-force">
						<fo:flow flow-name="xsl-region-body">

				      <fo:block margin-top="0.25in" margin-left="0.25in">
								<xsl:call-template name="back"/>
					    </fo:block>

						</fo:flow>
					</fo:page-sequence>
				</xsl:if>
					
			</xsl:for-each>

		</fo:root>
	</xsl:template>


	<xsl:template name="front">
		  <fo:block-container height="6in" margin-top="0.25in" 	margin-left="0.25in" background-image="url('C:\GP\Grainger\reorder\panelImages\MailPanel_6x9.pdf')" background-repeat="no-repeat">
			  <fo:block font="8pt Arial" >
			  	<fo:block margin-left="7.75in" margin-top="0.25in"><fo:external-graphic src="url('C:\GP\Grainger\reorder\panelImages\PRSRTMKTG.jpg')"/></fo:block>
		      <fo:block font="10pt Arial" margin-left="5in" margin-top="1in">
		      	<fo:block>*********<xsl:value-of select="endorse"/></fo:block>
		      	<fo:block font-size="8pt">
		      		T<fo:inline><xsl:value-of select="cont_id"/><xsl:value-of select="traymark_"/></fo:inline>&#160;&#160;
		      		B<fo:inline><xsl:value-of select="gpb_id"/><xsl:value-of select="pkgmark_"/></fo:inline>&#160;&#160;
		      		<fo:inline><xsl:value-of select="seq"/></fo:inline>
		      	</fo:block>
		      	<fo:block><xsl:value-of select="mktactivityid"/></fo:block>
						<fo:block><xsl:value-of select="fullname"/></fo:block>
						<fo:block><xsl:value-of select="titleslug"/></fo:block>
						<fo:block><xsl:value-of select="companyname"/></fo:block>
						<fo:block><xsl:value-of select="address1"/></fo:block>
						<fo:block><xsl:value-of select="address2"/></fo:block>
						<fo:block><xsl:value-of select="city"/>,&#160;<xsl:value-of select="state"/>&#160;<xsl:value-of select="zip"/><xsl:value-of select="zip4"/></fo:block>
						<fo:block font="16pt Barcode"><xsl:value-of select="imbarcode"/></fo:block>
					</fo:block>
				</fo:block>
	  </fo:block-container>
	  
	</xsl:template>
	
	<xsl:template name="back">
		
		  <fo:block-container height="6in" background-image="url('C:\GP\Grainger\reorder\panelImages\BackPanel_6x9.pdf')" background-repeat="no-repeat">
	      <fo:block font="10pt ArialMT" margin-left="0.75in" margin-right="0.75in" margin-top="1.25in">

			    <fo:table font-size="10pt" >
			        <fo:table-column column-width="2.25in"/>
			        <fo:table-column column-width="2.25in"/>
			        <fo:table-column column-width="2.25in"/>
			        <fo:table-body>
			            <fo:table-row height="2.5in">
										<xsl:variable name="prodCount" select="count(products/product)"/>
										<xsl:comment>prodCount: <xsl:value-of select="$prodCount"/></xsl:comment>
										
								    <fo:table-cell padding="0.2in">
												<xsl:call-template name="item">
													<xsl:with-param name="imageUrl" select="image1"/>
													<xsl:with-param name="itemNum" select="sku1"/>
													<xsl:with-param name="gisDesc" select="gisdesc1"/>
													<xsl:with-param name="shortDesc" select="shortdesc1"/>
												</xsl:call-template>
								    </fo:table-cell>											    
								    <fo:table-cell padding="0.2in">
												<xsl:call-template name="item">
													<xsl:with-param name="imageUrl" select="image2"/>
													<xsl:with-param name="itemNum" select="sku2"/>
													<xsl:with-param name="gisDesc" select="gisdesc2"/>
													<xsl:with-param name="shortDesc" select="shortdesc2"/>
												</xsl:call-template>
								    </fo:table-cell>											    
								    <fo:table-cell padding="0.2in">
												<xsl:call-template name="item">
													<xsl:with-param name="imageUrl" select="image3"/>
													<xsl:with-param name="itemNum" select="sku3"/>
													<xsl:with-param name="gisDesc" select="gisdesc3"/>
													<xsl:with-param name="shortDesc" select="shortdesc3"/>
												</xsl:call-template>
								    </fo:table-cell>


			            </fo:table-row>
			        </fo:table-body>
			    </fo:table>
				</fo:block>
	  </fo:block-container>
	  
	</xsl:template>



	<xsl:template name="item">
		<xsl:param name="imageUrl"/>
		<xsl:param name="itemNum"/>
		<xsl:param name="gisDesc"/>
		<xsl:param name="shortDesc"/>

		<xsl:variable name="description">
			<xsl:choose>
				<xsl:when test="string-length($gisDesc) &lt; 10">
					<xsl:value-of select="$shortDesc"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$gisDesc"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		
		<xsl:if test="string-length($itemNum) > 0">
	    <fo:block>

		    <fo:block width="2in">
					<fo:external-graphic src="url('{$imageUrl}')" content-height="11%"/> 
		    </fo:block>

				<xsl:comment>xxx: <xsl:value-of select="string-length($gisDesc)"/></xsl:comment>
		    <fo:block width="2in">
					<xsl:value-of select="$description"/>
		    </fo:block>
		    
		    <fo:block width="2in">
					Item No. <xsl:value-of select="$itemNum"/>
		    </fo:block>
	    </fo:block>
		</xsl:if>    
	    
	</xsl:template>

	
</xsl:stylesheet>
