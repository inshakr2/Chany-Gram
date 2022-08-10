
var page = 0;

// (1) 검색 페이지 로드하기
function resultLoad(page) {
	$.ajax({
		url:`/api/image/popular?page=${page}`,
		type:"GET",
		dataType:"json"
	}).done(res=>{
		res.data.forEach((image)=>{
			let item = getResultItem(image);
			$("#popularResult-List").append(item);
		})
	}).fail(error=>{
		console.log(error);
	});
}

resultLoad(page);

$(window).scroll(() => {

	let checkNum = $(window).scrollTop() - ($(document).height() - $(window).height());

	if (checkNum < 1 && checkNum > -1) {
		page += 1;
		resultLoad(page);

	}
});


function getResultItem(image) {
	let item = `<div class="p-img-box" id="${image.imageId}">
	<a href="#"> <img src="/upload/${image.postImageUrl}" />
	</a>
</div>`
	return item;
}
