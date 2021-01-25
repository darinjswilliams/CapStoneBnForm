# CapStoneBnForm

Consumer product safety application that will allow consumers to report information that is unsafe to the public. The concept of this application is allow the consumers to report on products that cause harm to the public. The information will be sent to the public database maintain by Safer Products.
where the reports of harm are reviewed by the U.S Consumer Product Safety Commission agency and manufacturer of the product. The application is a mobile application that will use the Safety Application Protocol Interface (API).

The SaferProducts.gov API provides access to all publically available information visible on SaferProducts.gov. Here is the information that is provided:

Incident - Incident Report Number, Incident Date, Incident Location, Incident Description, Incident Report Publication Date, Locale
Manufacturer - Manufacturer Name, Manufacturer Location, Manufacturer Notification Date, Manufacturer comments
Product - Product Brand Name, Product Model Name, Product Manufactured Date, Product Purchased Date, Incident Product Description
Retail Company - Retail Company Name, Retail Company Location
Victim - Victim Gender, Victim Age
In addition, CPSC has a Recalls API. The Recalls API provides the following fields of information: Recall Number, Company, Product Type, Product Description, Hazard, County/Administrative Area of Manufacture, Recall Date, UPC (when provided in a recall). Here’s the full documentation on the Recalls API.

Available Recall Retrieval Search Parameters
  
The Recall retrieval web services perform a case insensitive search for any or all of the following fields using a wildcard search. Data is returned as XML, or optionally as JSON:
RecallNumber RecallDateStart RecallDateEnd LastPublishDateStart LastPublishDateEnd RecallURL
RecallTitle ConsumerContact
RecallDescription ProductName ProductDescription ProductModel
ProductType RecallInconjunctionCountry ImageURL
Injury
Manufacturer ManufacturerCountry UPC
Hazard
Remedy
Retailer

In addition, a non‐field parameter is available:
format: determines the output format. Possible values are XML or JSON. The default value is XML .
Desired search parameters are to be appended to the root URI in the form of HTML query strings, as in the following example, which is an URI to retrieve recalls such that the title contains the string "child" and the description contains the string "metal":

http://www.saferproducts.gov/RestWebServices/Recall?RecallTitle=child&RecallDescription=metal

Note that neither "child" nor "metal" need be an actual word in the respective fields. For instance, a recall had the proper noun Fairchild in the title and the qualifier non‐metallic in the description would be a match for the above request.
The following URI will retrieve the same result set as above in Json format:

# Features
Scan BarCode and QR Code
Firebase UI Login
Firebase Analytics
Share Information between users
Widget
Report Information to Consumer Product Safety Board

#Third Party Integration
Picasso
Retrofit
Room
Safety Api
Google Play Services
Firebase Analytics
Firebase UI Authentication



http://www.saferproducts.gov/RestWebServices/Recall?format=json&RecallTitle=Child&RecallDescription=metal
