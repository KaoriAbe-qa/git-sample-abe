package com.example.controller;
//tanimoto
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.domain.Ex17;
import com.example.form.Ex17Form;

@Controller
@RequestMapping("/ex17")
public class Ex17Controller {

	//入力画面の表示
	@RequestMapping("")
	public String index(Model model) {

		//今回はここで要素を入れる
		Map<Integer,String> hobbyMap = new LinkedHashMap<>();
		hobbyMap.put(1, "テニス");
		hobbyMap.put(2, "野球");
		hobbyMap.put(3, "ゴルフ");

		//hobbyMapというキーでスコープに格納
		model.addAttribute("hobbyMap",hobbyMap);

		//言語の要素もここから追加
		Map<Integer,String> langMap = new LinkedHashMap<>();
		langMap.put(1, "Java");
		langMap.put(2, "Python");
		langMap.put(3, "Ruby");

		//langMapというキーでスコープに格納
		model.addAttribute("langMap", langMap);

		//userフォルダのex-17-input.htmlを指す
		return "user/ex-17-input";
	}

	@ModelAttribute
	public Ex17Form setUpForm() {
		return new Ex17Form();
	}

	@RequestMapping("/create")
	public String create(@Validated Ex17Form form
			, BindingResult result
			, RedirectAttributes redirectAttributes
			, Model model) {

		//もしエラーがあったら遷移する
		if(result.hasErrors()) {
			return index(model);
		}

		//オブジェクト
		Ex17 ex17 = new Ex17();

		//フォームからドメインにコピーできることをコピー
		BeanUtils.copyProperties(form, ex17);

		//型の違うhobbyListとlangListを手動でコピー
		List<String> hobbyList = new ArrayList<>();
		for(Integer hobbyCode:form.getHobbyList()) {
			switch(hobbyCode) {
			case 1:
				hobbyList.add("テニス");
				break;
			case 2:
				hobbyList.add("野球");
				break;
			case 3:
				hobbyList.add("ゴルフ");
				break;
			}
		}
		ex17.setHobbyList(hobbyList);

		List<String> langList = new ArrayList<>();
		for(Integer langCode:form.getLangList()) {
			switch(langCode) {
			case 1:
				langList.add("Java");
				break;
			case 2:
				langList.add("Python");
				break;
			case 3:
				langList.add("Ruby");
				break;
			}
		}
		ex17.setLangList(langList);

		//今回はflashスコープに格納
		redirectAttributes.addFlashAttribute("ex17", ex17);

		//登録完了画面にフォワードするメソッドにリダイレクト
		//↓のはURL
		return "redirect:/ex17/ex17result";
	}

	@RequestMapping("/ex17result")
	public String ex17result() {
		//フォワード
		//↓のはhtml
		return "user/ex-17-result";
	}

}
