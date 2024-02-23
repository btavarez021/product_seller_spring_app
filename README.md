INSERT PRODUCT

1. Always post a seller first

	In postman use the following JSON:

		{

		"sellerName":"SELLERNAMEHERE"
		}
	
2. Post Product.

	In post man use the following JSON. Fill values with your own.

		{
			"productName": "PRODUCTNAMEHERE",
			"price": 0,
			"sellerName": "SELLERNAMEHERE"
		}
		

Update Product

1. go to postman and input following URI: localhost:9001/products/PRODUCTIDHERE

2. 

	{
		"productName": "PRODUCTNAMEHERE",
		"price": 0.00,
		"sellerName": "SELLERNAMEHERE"
	}
	
DELETE PRODUCT

This will delete the product using the product ID

1. Go to postman and input following URI: localhost:9001/products/PRODUCTIDHERE


