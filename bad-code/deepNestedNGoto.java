
private  boolean check(List<Attr> attrList, Map<String, List<Product>> dtos, Group group){
	boolean result = false;
	look:
	for (Attr attr : attrList) {
		if ("Y".equals(attr.getIfShow())) {
			List<Product> list = dtos.get(group.name());
			if(list !=null){
				for (Product dto : list) {
					if (StringUtils.isNotEmpty(dto.getImperialValue()) || StringUtils.isNotEmpty(dto.getMetricValue())) {
						result = true;
						break look;
					}
				}
			}
		}
	}
	return result;
}

