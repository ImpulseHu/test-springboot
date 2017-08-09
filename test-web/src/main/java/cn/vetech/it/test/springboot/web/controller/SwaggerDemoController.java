package cn.vetech.it.test.springboot.web.controller;

import cn.vetech.it.test.springboot.web.vo.FooVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 一段话描述
 *
 * @author huyu 8979@vetech.cn
 * @date 2017-08-09
 */
@Api(value = "这是一个Swagger例子")
@RestController
@RequestMapping("/foo")
public class SwaggerDemoController {

	private static Map<Long, FooVo> fooMap = Collections.synchronizedMap(new HashMap<Long, FooVo>());

	@ApiOperation(value="获取foo列表", notes="")
	@RequestMapping(value={"","list"}, method= RequestMethod.GET)
	public List<FooVo> getUserList() {
		List<FooVo> r = new ArrayList<FooVo>(fooMap.values());
		return r;
	}

	@ApiOperation(value="创建foo", notes="根据FooVo对象创建foo")
	@ApiImplicitParam(name = "foo", value = "FooVo实体", required = true, dataType = "FooVo")
	@RequestMapping(value="save", method=RequestMethod.POST)
	public String save(@RequestBody FooVo foo) {
		fooMap.put(foo.getId(), foo);
		return "success";
	}

	@ApiOperation(value="获取foo详细信息", notes="根据url的id来获取foo详细信息")
	@ApiImplicitParam(name = "id", paramType = "path", value = "foo主键id", required = true, defaultValue = "0", dataType = "Long")
	@RequestMapping(value="/detail/{id}", method=RequestMethod.GET)
	public FooVo detail(@PathVariable(name = "id") Long id) {
		return fooMap.get(id);
	}

	@ApiOperation(value="更新foo详细信息", notes="根据url的id来指定更新对象，并根据传过来的FooVo信息来更新foo详细信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "foo", value = "foo详细实体FooVo", required = true, dataType = "FooVo")
	})
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(@RequestBody FooVo foo) {
		if (foo.getId() == null || fooMap.get(foo.getId()) == null) {
			return "failure";
		}
		FooVo u = fooMap.get(foo.getId());
		u.setName(foo.getName());
		u.setTime(foo.getTime());
		fooMap.put(foo.getId(), u);
		return "success";
	}

	@ApiOperation(value="删除foo", notes="根据url的id来指定删除对象")
	@ApiImplicitParam(name = "id", paramType = "path", value = "foo主键ID", required = true, defaultValue = "0", dataType = "Long")
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public String delete(@PathVariable(name = "id") Long id) {
		fooMap.remove(id);
		return "success";
	}

}
