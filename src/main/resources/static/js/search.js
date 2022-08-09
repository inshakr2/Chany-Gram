let searchTagId = $("#tagId").val();

var tempArr = new Array();
document.querySelectorAll(".p-img-box img").forEach(
	(img)=> {
		tempArr.push(img.getAttribute("name"));
	}
);
var data = {"popularIds":tempArr, "tagId":searchTagId, "lastImageId":Number.MAX_SAFE_INTEGER};

// (1) 검색 페이지 로드하기
function resultLoad(data) {
	$.ajax({
		url:`/api/image/search`,
		type:"POST",
		data:JSON.stringify(data),
		contentType:"application/json; charset=UTF-8",
		dataType:"json"
	}).done(res=>{
		res.data.forEach((image)=>{
			let item = getResultItem(image);
			$("#searchResult-List").append(item);
		})
	}).fail(error=>{
		console.log(error);
	});
}
// Number.MAX_SAFE_INTEGER (9007199254740991.. 약 9천조)
// 최초 로딩시 가장 작은 id값이 없기 때문에 해당 값으로 로딩 차후 변경 필요한 시점이 있을 수 있음.
resultLoad(data);

// (1) 스토리 스크롤 페이징
function getLastImageId() {

	var id = [];
	var story = document.getElementsByClassName('p-img-box');

	for(let i = 0; i < story.length; i++)  {
		id.push(story[i].id);
	  }
	id = Math.min.apply(null, id);

	return id;
}

$(window).scroll(() => {

	let checkNum = $(window).scrollTop() - ($(document).height() - $(window).height());

	if (checkNum < 1 && checkNum > -1) {

		var lastImageId = getLastImageId();
		var nextData = {"popularIds":tempArr, "tagId":searchTagId, "lastImageId":lastImageId};

		resultLoad(nextData);

	}
});


function getResultItem(image) {
	let item = `<div class="p-img-box" onclick="modalDetail(${image.imageId})" id="${image.imageId}">
	<a href="#"> <img src="/upload/${image.postImageUrl}" />
	</a>
</div>`
	return item;
}

